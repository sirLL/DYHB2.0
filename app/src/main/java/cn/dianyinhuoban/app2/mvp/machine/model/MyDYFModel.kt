package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.MyMachineDYFBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyDYFContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class MyDYFModel : BaseModel(), MyDYFContract.Model {

    override fun fetchMyDYF(
        page: Int,
        pageSize: Int,
        type: String
    ): Observable<Response<MyMachineDYFBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchMyMachineDYF(
                MMKVUtil.getUserID(),
                MMKVUtil.getToken(), page, pageSize, type
            )
    }
}