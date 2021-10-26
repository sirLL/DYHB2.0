package cn.dianyinhuoban.app2.mvp.home.view

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.ShopAppItemBean
import cn.dianyinhuoban.app2.mvp.home.contract.ShopAppContract
import cn.dianyinhuoban.app2.mvp.home.presenter.ShopAppPresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter
import com.wareroom.lib_base.utils.BitmapUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ShopCodeFragment : BaseListFragment<ShopAppItemBean, ShopAppContract.Presenter?>(),
    ShopAppContract.View {

    companion object {
        fun newInstance(): ShopCodeFragment {
            val args = Bundle()
            val fragment = ShopCodeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun isSupportLoadMore(): Boolean {
        return false
    }

    override fun getPresenter(): ShopAppContract.Presenter? {
        return ShopAppPresenter(this)
    }

    override fun getItemLayout(): Int {
        return R.layout.item_shop_code
    }

    override fun onRequest(page: Int) {
        mPresenter?.fetchShopApp()
    }

    override fun bindShopApp(data: List<ShopAppItemBean>?) {
        loadData(data)
    }

    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: ShopAppItemBean?
    ) {
        val ivQR = viewHolder?.getView<ImageView>(R.id.iv_qr)
        viewHolder?.setText(R.id.tv_title, itemData?.name ?: "")
        itemData?.let {
            createQR(it.url, ivQR, position)
        }
        viewHolder?.getView<TextView>(R.id.tv_save)?.setOnClickListener {
            saveView(viewHolder.itemView)
        }
        ivQR?.setOnClickListener {
            itemData?.let { app ->
                if (!app.url.isNullOrBlank() && app.url!!.startsWith("http")) {
                    val intent = Intent()
                    intent.action = "android.intent.action.VIEW"
                    val uri = Uri.parse(app.url)
                    intent.data = uri
                    startActivity(intent)
                }
            }
        }
    }

    override fun onItemClick(data: ShopAppItemBean?, position: Int) {

    }

    private fun createQR(qrContent: String?, ivQR: ImageView?, position: Int) {
        ivQR?.let {
            if (qrContent.isNullOrBlank()) {
                it.setImageBitmap(null)
                mAdapter?.notifyItemChanged(position)
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
                            mAdapter?.notifyItemChanged(position)
                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onComplete() {}
                    })
            }
        }
    }

    private fun saveView(view: View?) {
        view?.let {
            RxPermissions(ShareQRActivity@ this).request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).subscribe { aBoolean: Boolean ->
                if (aBoolean) {
                    saveView2File(view)
                } else {
                    showToast("你尚未开启读写权限")
                }
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