package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.ExchangeRecordItemBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeRecordContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.ExchangeRecordPresenter
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter

class ExchangeRecordFragment :
    BaseListFragment<ExchangeRecordItemBean, ExchangeRecordContract.Presenter?>(),
    ExchangeRecordContract.View {

    companion object {
        fun newInstance(): ExchangeRecordFragment {
            val args = Bundle()
            val fragment = ExchangeRecordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getItemLayout(): Int {
        return R.layout.item_exchange_record
    }

    override fun getPresenter(): ExchangeRecordContract.Presenter? {
        return ExchangeRecordPresenter(this)
    }

    override fun onRequest(page: Int) {
        mPresenter?.fetchExchangeRecord(page, DEF_PAGE_SIZE)
    }


    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: ExchangeRecordItemBean?
    ) {
        viewHolder?.setText(R.id.tv_title, itemData?.goods_name ?: "")
        viewHolder?.setText(R.id.tv_count, itemData?.exchange ?: "")
        viewHolder?.setText(R.id.tv_date, itemData?.add_time ?: "")
    }

    override fun onItemClick(data: ExchangeRecordItemBean?, position: Int) {
        data?.let {
            ExchangeRecordDetailActivity.openExchangeRecordDetail(requireContext(), it)
        }
    }

    override fun bindExchangeRecord(data: List<ExchangeRecordItemBean>?) {
        loadData(data)
    }
}