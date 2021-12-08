package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.MyMachineDYFBean
import cn.dianyinhuoban.app2.bean.MyMachinePOSBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface MyDYFContract {
    interface Model : IModel {
        fun fetchMyDYF(
            page: Int,
            pageSize: Int,
            type: String
        ): Observable<Response<MyMachineDYFBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchMyDYF(
            page: Int,
            pageSize: Int,
            type: String
        )
    }

    interface View : IView {
        fun bindMyDYF(bean: MyMachineDYFBean?)
    }
}