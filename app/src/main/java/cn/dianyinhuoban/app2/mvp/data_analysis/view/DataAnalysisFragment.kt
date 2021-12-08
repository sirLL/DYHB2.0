package cn.dianyinhuoban.app2.mvp.data_analysis.view

import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter

class DataAnalysisFragment :BaseListFragment<Any,IPresenter?>(){

    override fun getContentView(): Int {
        return super.getContentView()
    }
    override fun getPresenter(): IPresenter? {
        TODO("Not yet implemented")
    }

    override fun onRequest(page: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemLayout(): Int {
        TODO("Not yet implemented")
    }

    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: Any?
    ) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(data: Any?, position: Int) {
        TODO("Not yet implemented")
    }
}