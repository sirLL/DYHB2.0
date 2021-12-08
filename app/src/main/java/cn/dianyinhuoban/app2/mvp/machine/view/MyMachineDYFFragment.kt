package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.DYFItemBean
import cn.dianyinhuoban.app2.bean.MyMachineDYFBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyDYFContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.MyDYFPresenter
import cn.dianyinhuoban.app2.widget.dialog.MachineInviteDialog
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter
import com.wareroom.lib_base.widget.DividerDecoration

class MyMachineDYFFragment : BaseListFragment<DYFItemBean?, MyDYFContract.Presenter?>(),
    MyDYFContract.View {

    companion object {
        fun newInstance(): MyMachineDYFFragment {
            val args = Bundle()
            val fragment = MyMachineDYFFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var tvCodeNum: TextView? = null

    override fun getContentView(): Int {
        return R.layout.fragment_my_machine_dyf
    }

    override fun getItemLayout(): Int {
        return R.layout.item_my_machine_dyf
    }

    override fun getPresenter(): MyDYFContract.Presenter? {
        return MyDYFPresenter(this)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return DividerDecoration(
            ContextCompat.getColor(requireContext(), R.color.dy_color_divider), 0
        )
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        tvCodeNum = contentView?.findViewById(R.id.tv_code_num)
    }

    override fun onRequest(page: Int) {
        mPresenter?.fetchMyDYF(page, 20, "1")
    }

    override fun bindMyDYF(bean: MyMachineDYFBean?) {
        loadData(bean?.arylist ?: mutableListOf())
        tvCodeNum?.text = bean?.invitecount ?: ""
    }

    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: DYFItemBean?
    ) {
        viewHolder?.setText(R.id.tv_title, itemData?.name ?: "")
        viewHolder?.setText(R.id.tv_code, itemData?.code_no ?: "")
        viewHolder?.getView<TextView>(R.id.tv_fee_detail)?.setOnClickListener {
            itemData?.rate_id?.let {
                MyMachineDYFRateActivity.openMyMachineDYFRateActivity(requireContext(), it)
            }
        }
        viewHolder?.getView<TextView>(R.id.tv_invite_button)?.setOnClickListener {
            if (!itemData?.qrcode_url.isNullOrBlank() && !itemData?.code_no.isNullOrBlank()) {
                showInviteDialog(itemData?.code_no!!, itemData?.qrcode_url!!)
            }
        }
    }

    override fun onItemClick(data: DYFItemBean?, position: Int) {

    }

    private fun showInviteDialog(qr: String, url: String) {
        MachineInviteDialog.newInstance(qr, url).show(childFragmentManager, "MachineInviteDialog")
    }
}