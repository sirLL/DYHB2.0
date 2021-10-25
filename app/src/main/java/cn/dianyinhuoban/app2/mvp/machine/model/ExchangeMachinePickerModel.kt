package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeMachinePickerContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class ExchangeMachinePickerModel : BaseModel(), ExchangeMachinePickerContract.Model {
    override fun fetchExchangeMachine(
        page: Int,
        pageSize: Int
    ): Observable<Response<ExchangeMachineBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchExchangeMachine(MMKVUtil.getUserID(), MMKVUtil.getToken(), page, pageSize)
    }
}