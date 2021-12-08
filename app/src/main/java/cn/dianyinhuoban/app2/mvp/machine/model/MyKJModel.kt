package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.bean.MyMachineKJBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyKJContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class MyKJModel : BaseModel(), MyKJContract.Model {
    override fun fetchMyMachineKJ(
        page: Int,
        pageSize: Int
    ): Observable<Response<MyMachineKJBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchMyMachineKJ(MMKVUtil.getUserID(), MMKVUtil.getToken(), page, pageSize)
    }

    override fun submitTransfer(
        num: String,
        underID: String
    ): Observable<Response<EmptyBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .submitTransfer(MMKVUtil.getUserID(), MMKVUtil.getToken(), "vf", num, underID)
    }
}