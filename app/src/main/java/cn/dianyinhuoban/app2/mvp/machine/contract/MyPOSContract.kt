package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.MyMachinePOSBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface MyPOSContract {
    interface Model : IModel {
        fun fetchMyPOS(
            page: Int,
            pageSize: Int,
            keyword: String,
            type: String
        ): Observable<Response<MyMachinePOSBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchMyPOS(
            page: Int,
            pageSize: Int,
            keyword: String,
            type: String
        )
    }

    interface View : IView {
        fun bindMyPos(posBean: MyMachinePOSBean?)
    }
}