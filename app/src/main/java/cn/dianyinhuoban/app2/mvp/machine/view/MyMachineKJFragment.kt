package cn.dianyinhuoban.app2.mvp.machine.view

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MyMachineKJItemBean
import cn.dianyinhuoban.app2.bean.MyMachineKJBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyKJContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.MyKJPresenter
import cn.dianyinhuoban.app2.widget.dialog.MachineTransferEditDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter
import com.wareroom.lib_base.utils.BitmapUtils
import com.wareroom.lib_base.utils.DateTimeUtils
import com.wareroom.lib_base.utils.OSUtils
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_base.widget.DividerDecoration
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MyMachineKJFragment : BaseListFragment<MyMachineKJItemBean, MyKJContract.Presenter?>(),
    MyKJContract.View {
    companion object {
        fun newInstance(): MyMachineKJFragment {
            val args = Bundle()
            val fragment = MyMachineKJFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var ivQR: ImageView? = null
    private var tvAvailable: TextView? = null
    private var tvInvited: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun getContentView(): Int {
        return R.layout.fragment_my_machine_kj
    }

    override fun getPresenter(): MyKJContract.Presenter? {
        return MyKJPresenter(this)
    }

    override fun getItemLayout(): Int {
        return R.layout.item_my_machine_kj
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return DividerDecoration(
            ContextCompat.getColor(
                requireContext(),
                com.wareroom.lib_base.R.color.dy_color_divider
            ), 0
        )
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        ivQR = contentView?.findViewById(R.id.iv_qr)
        tvAvailable = contentView?.findViewById(R.id.tv_available)
        tvInvited = contentView?.findViewById(R.id.tv_invited)
        progressBar = contentView?.findViewById(R.id.progress_bar)

        contentView?.findViewById<Button>(R.id.btn_use)?.setOnClickListener {
            RxPermissions(MyMachineKJFragment@ this).request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).subscribe { aBoolean: Boolean ->
                if (aBoolean) {
                    saveView2File(it)
                } else {
                    showToast("你尚未开启读写权限")
                }
            }
        }
        createQR(MMKVUtil.getUserID(), ivQR)
    }

    override fun onRequest(page: Int) {
        mPresenter?.fetchMyMachineKJ(page, DEF_PAGE_SIZE)
    }


    override fun bindMyMachineKJ(bean: MyMachineKJBean?) {
        tvAvailable?.text = "可使用次数: ${bean?.partnerVfSum ?: "--"}"
        tvInvited?.text = "已邀请人数: ${bean?.count ?: "--"}"
        loadData(bean?.arylist)
    }

    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: MyMachineKJItemBean?
    ) {
        viewHolder?.setText(
            R.id.tv_title, if (itemData?.merName.isNullOrBlank()) {
                "--"
            } else {
                itemData?.merName
            }
        )
        viewHolder?.setText(
            R.id.tv_phone, if (itemData?.mobile.isNullOrBlank()) {
                "--"
            } else {
                itemData?.mobile
            }
        )
        viewHolder?.setText(
            R.id.tv_count, if (itemData?.member_partnerVfSum.isNullOrBlank()) {
                "--"
            } else {
                itemData?.member_partnerVfSum
            }
        )
        viewHolder?.setText(
            R.id.tv_date, if (itemData?.inputtime.isNullOrBlank() || "0" == itemData?.inputtime) {
                "--"
            } else {
                DateTimeUtils.formatDate(
                    itemData?.inputtime?.toLong()!! * 1000,
                    DateTimeUtils.PATTERN_YYYY_MM_DD_HH_MM_CHAR
                )
            }
        )
        val transferButton = viewHolder?.getView<TextView>(R.id.tv_transfer)
        transferButton?.visibility =
            if (itemData?.member_partnerVfSum.isNullOrBlank() || "0" == itemData?.member_partnerVfSum) {
                View.GONE
            } else {
                View.VISIBLE
            }
        transferButton?.setOnClickListener {
            itemData?.let {
                showEditDialog(it)
            }
        }
        viewHolder?.getView<TextView>(R.id.tv_call)?.setOnClickListener {
            itemData?.mobile?.let {
                OSUtils.callPhone(requireContext(), it)
            }
        }
    }

    override fun onItemClick(data: MyMachineKJItemBean?, position: Int) {

    }

    private fun showEditDialog(machineKJItemBean: MyMachineKJItemBean) {
        MachineTransferEditDialog.newInstance(machineKJItemBean)
            .setOnMachineTransferConfirmCallback(object :
                MachineTransferEditDialog.OnMachineTransferConfirmCallback {
                override fun getTransferQuantity(
                    machineItem: MyMachineKJItemBean,
                    quantity: String
                ) {
                    mPresenter?.submitTransfer(quantity, machineItem.uid ?: "")
                }
            })
            .show(childFragmentManager, "MachineTransferEditDialog")
    }

    override fun onTransferSuccess() {
        showToast("划拨成功")
        autoRefresh()
    }

    private fun createQR(qrContent: String?, ivQR: ImageView?) {
        ivQR?.let {
            if (qrContent.isNullOrBlank()) {
                it.setImageBitmap(null)
            } else {
                Observable.just(qrContent)
                    .map { s: String? ->
                        QRCodeEncoder.syncEncodeQRCode(
                            s,
                            400,
                            Color.parseColor("#000000")
                        )
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Bitmap> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(bitmap: Bitmap) {
                            it.setImageBitmap(bitmap)
                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onComplete() {}
                    })
            }
        }
    }

    private fun saveView2File(view: View) {
        Observable.just(view)
            .map { v ->
                val fileName = "DYHB${Calendar.getInstance().timeInMillis}.jpg"
                BitmapUtils.saveBitmap(
                    requireContext(),
                    BitmapUtils.view2Bitmap(v),
                    fileName
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(path: String) {
                    showToast("图片保存至${path}")
                }

                override fun onError(e: Throwable) {
                    showToast("图片保存失败")
                }

                override fun onComplete() {}
            })
    }
}