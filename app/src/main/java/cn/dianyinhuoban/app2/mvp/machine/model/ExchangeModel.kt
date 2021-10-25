package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class ExchangeModel : BaseModel(), ExchangeContract.Model {
    override fun fetchAddress(): Observable<Response<AddressBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchAddress(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }

    override fun fetchExchangeMachine(
        page: Int,
        pageSize: Int
    ): Observable<Response<ExchangeMachineBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchExchangeMachine(MMKVUtil.getUserID(), MMKVUtil.getToken(), page, pageSize)
    }

    override fun submitExchangeMachine(
        count: String,
        type: String,
        name: String,
        phone: String,
        address: String,
        password: String
    ): Observable<Response<List<EmptyBean?>?>> {
        return mRetrofit.create(ApiService::class.java)
            .submitExchangeMachine(
                MMKVUtil.getUserID(),
                MMKVUtil.getToken(),
                count,
                type,
                name,
                phone,
                address,
                password
            )
    }
}