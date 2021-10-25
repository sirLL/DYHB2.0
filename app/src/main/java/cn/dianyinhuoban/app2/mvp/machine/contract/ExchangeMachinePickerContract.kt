package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable


interface ExchangeMachinePickerContract {
    interface Model : IModel {
        fun fetchExchangeMachine(
            page: Int,
            pageSize: Int
        ): Observable<Response<ExchangeMachineBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchExchangeMachine(
            page: Int,
            pageSize: Int
        )
    }

    interface View : IView {
        fun bindExchangeMachine(machineBean: ExchangeMachineBean?)
    }
}