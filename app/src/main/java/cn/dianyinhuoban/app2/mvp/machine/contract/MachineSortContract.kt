package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.MachineSortBean
import cn.dianyinhuoban.app2.bean.MachineSortItemBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface MachineSortContract {
    interface Model : IModel {
        fun fetchSort(): Observable<Response<MachineSortBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchSort()
    }

    interface View : IView {
        fun bindSort(data: List<MachineSortItemBean?>?)
    }
}