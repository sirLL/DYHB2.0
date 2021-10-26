package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.ExchangeRecordBean
import cn.dianyinhuoban.app2.bean.ExchangeRecordItemBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface ExchangeRecordContract {
    interface Model : IModel {
        fun fetchExchangeRecord(page: Int, pageSize: Int): Observable<Response<ExchangeRecordBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchExchangeRecord(page: Int, pageSize: Int)
    }

    interface View : IView {
        fun bindExchangeRecord(data: List<ExchangeRecordItemBean>?)
    }
}