package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.ExchangeRecordBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeRecordContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class ExchangeRecordModel : BaseModel(), ExchangeRecordContract.Model {
    override fun fetchExchangeRecord(
        page: Int,
        pageSize: Int
    ): Observable<Response<ExchangeRecordBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchExchangeRecord(MMKVUtil.getUserID(), MMKVUtil.getToken(), page, pageSize)
    }
}