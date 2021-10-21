package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.MachineBean
import cn.dianyinhuoban.app2.bean.UserInfoBean
import cn.dianyinhuoban.app2.mvp.machine.contract.BuyMachineContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class BuyMachineModel : BaseModel(), BuyMachineContract.Model {
    override fun fetchMerchant(): Observable<Response<UserInfoBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchMerchant(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }

    override fun fetchMachine(type: String): Observable<Response<MachineBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchMachine(MMKVUtil.getUserID(), MMKVUtil.getToken(), type)
    }
}