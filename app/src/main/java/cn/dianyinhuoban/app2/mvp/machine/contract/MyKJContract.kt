package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.bean.MyMachineKJBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable
import retrofit2.http.Field

interface MyKJContract {
    interface Model : IModel {
        fun fetchMyMachineKJ(page: Int, pageSize: Int): Observable<Response<MyMachineKJBean?>>

        fun submitTransfer(
            num: String,
            underID: String
        ): Observable<Response<EmptyBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchMyMachineKJ(page: Int, pageSize: Int)

        fun submitTransfer(
            num: String,
            underID: String
        )
    }

    interface View : IView {
        fun bindMyMachineKJ(bean: MyMachineKJBean?)

        fun onTransferSuccess()
    }
}