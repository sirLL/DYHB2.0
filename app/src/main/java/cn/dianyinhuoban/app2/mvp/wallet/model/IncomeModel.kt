package cn.dianyinhuoban.app2.mvp.wallet.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.IncomeBean
import cn.dianyinhuoban.app2.bean.IntegralBean
import cn.dianyinhuoban.app2.mvp.wallet.contract.IncomeContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class IncomeModel : BaseModel(), IncomeContract.Model {
    override fun fetchIncome(): Observable<Response<IncomeBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchIncome(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }

    override fun fetchIntegral(): Observable<Response<IntegralBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchIntegral(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }
}