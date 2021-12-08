package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.MyMachineDYFRateBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface MyDYFRateContract {
    interface Model : IModel {
        fun fetchMyDYFRate(
            rateID: String
        ): Observable<Response<MyMachineDYFRateBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchMyDYFRate(
            rateID: String
        )
    }

    interface View : IView {
        fun bindMyDYFRate(bean: MyMachineDYFRateBean?)
    }
}