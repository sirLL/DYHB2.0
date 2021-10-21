package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.MachineBean
import cn.dianyinhuoban.app2.bean.UserInfoBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface BuyMachineContract {
    interface Model : IModel {
        fun fetchMerchant(): Observable<Response<UserInfoBean?>>

        fun fetchMachine(type: String): Observable<Response<MachineBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchMerchant()

        fun fetchMachine(type: String)
    }

    interface View : IView {
        fun bindMerchant(merchantBean: UserInfoBean?)

        fun bindMachine(machineBean: MachineBean?)
    }
}