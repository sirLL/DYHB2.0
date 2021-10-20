package cn.dianyinhuoban.app2.mvp.wallet.contract

import cn.dianyinhuoban.app2.bean.IncomeBean
import cn.dianyinhuoban.app2.bean.IntegralBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface IncomeContract {
    interface Model : IModel {
        fun fetchIncome(): Observable<Response<IncomeBean?>>

        fun fetchIntegral(): Observable<Response<IntegralBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchIncome()

        fun fetchIntegral()
    }

    interface View : IView {
        fun bindIncome(incomeBean: IncomeBean?)

        fun bindIntegral(integralBean: IntegralBean?)
    }
}