package cn.dianyinhuoban.app2.mvp.me.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.mvp.me.contract.AddressContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class AddressModel : BaseModel(), AddressContract.Model {
    override fun fetchAddress(): Observable<Response<AddressBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchAddress(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }

    override fun submitDefAddress(addressID: String): Observable<Response<EmptyBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .submitDefAddress(MMKVUtil.getUserID(), MMKVUtil.getToken(), addressID)
    }

    override fun deleteAddress(addressID: String): Observable<Response<EmptyBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .deleteAddress(MMKVUtil.getUserID(), MMKVUtil.getToken(), addressID)
    }
}