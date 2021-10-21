package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface ExchangeContract {
    interface Model : IModel {
        fun fetchAddress(): Observable<Response<AddressBean?>>

        fun fetchExchangeMachine(
            page: Int,
            pageSize: Int
        ): Observable<Response<ExchangeMachineBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchAddress()

        fun fetchExchangeMachine(page: Int, pageSize: Int)
    }

    interface View : IView {
        fun bindAddress(addressBean: AddressBean?)

        fun bindExchangeMachine(machineBean: ExchangeMachineBean?)
    }
}