package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.MyMachinePOSBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyPOSContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class MyPOSModel : BaseModel(), MyPOSContract.Model {
    override fun fetchMyPOS(
        page: Int,
        pageSize: Int,
        keyword: String,
        type: String
    ): Observable<Response<MyMachinePOSBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchMyMachinePOS(
                MMKVUtil.getUserID(),
                MMKVUtil.getToken(),
                page,
                pageSize,
                keyword,
                type
            )
    }
}