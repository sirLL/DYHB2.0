package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MachineTransferRecordBean
import cn.dianyinhuoban.app2.bean.MachineTransferRecordItemBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MachineTransferRecordContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.MachineTransferRecordPresenter
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter
import com.wareroom.lib_base.utils.DateTimeUtils
import com.wareroom.lib_base.utils.OSUtils
import com.wareroom.lib_base.widget.DividerDecoration

class MachineTransferRecordFragment :
    BaseListFragment<MachineTransferRecordItemBean, MachineTransferRecordContract.Presenter?>(),
    MachineTransferRecordContract.View {

    companion object {
        fun newInstance(): MachineTransferRecordFragment {
            val args = Bundle()
            val fragment = MachineTransferRecordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return DividerDecoration(
            ContextCompat.getColor(requireContext(), R.color.dy_color_divider), 0
        )
    }

    override fun getPresenter(): MachineTransferRecordContract.Presenter? {
        return MachineTransferRecordPresenter(this)
    }

    override fun onRequest(page: Int) {
        mPresenter?.fetchTransferRecord(page, DEF_PAGE_SIZE)
    }

    override fun getItemLayout(): Int {
        return R.layout.item_machine_transfer_record
    }

    override fun bindTransferRecord(data: MachineTransferRecordBean?) {
        loadData(data?.arylist)
    }

    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: MachineTransferRecordItemBean?
    ) {
        viewHolder?.setText(
            R.id.tv_phone, if (itemData?.partner_telephone.isNullOrBlank()) {
                "--"
            } else {
                itemData?.partner_telephone
            }
        )
        viewHolder?.setText(
            R.id.tv_count, if (itemData?.num.isNullOrBlank()) {
                "划拨次数:--"
            } else {
                "划拨次数:${itemData?.num}"
            }
        )
        viewHolder?.setText(
            R.id.tv_date, if (itemData?.add_time.isNullOrBlank() || "0" == itemData?.add_time) {
                "--"
            } else {
                DateTimeUtils.formatDate(
                    itemData?.add_time?.toLong()!! * 1000,
                    DateTimeUtils.PATTERN_YYYY_MM_DD_HH_MM_CHAR
                )
            }
        )
        viewHolder?.getView<TextView>(R.id.tv_call)?.setOnClickListener {
            itemData?.partner_telephone?.let {
                OSUtils.callPhone(requireContext(), itemData?.partner_telephone)
            }
        }
    }

    override fun onItemClick(data: MachineTransferRecordItemBean?, position: Int) {

    }
}