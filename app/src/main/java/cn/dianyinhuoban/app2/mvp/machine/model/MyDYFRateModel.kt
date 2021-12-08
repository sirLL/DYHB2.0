package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.MyMachineDYFRateBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyDYFRateContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class MyDYFRateModel : BaseModel(), MyDYFRateContract.Model {
    override fun fetchMyDYFRate(
        rateID: String
    ): Observable<Response<MyMachineDYFRateBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchMyDYFRate(MMKVUtil.getUserID(), MMKVUtil.getToken(), rateID)
    }
}