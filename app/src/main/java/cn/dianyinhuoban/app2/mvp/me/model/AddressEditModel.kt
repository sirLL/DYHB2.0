package cn.dianyinhuoban.app2.mvp.me.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.mvp.me.contract.AddressEditContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class AddressEditModel : BaseModel(), AddressEditContract.Model {
    override fun submitAddress(
        addressID: String,
        name: String,
        phone: String,
        area: String,
        address: String,
        isDefault: Boolean
    ): Observable<Response<EmptyBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .submitAddress(
                MMKVUtil.getUserID(),
                MMKVUtil.getToken(),
                addressID,
                name,
                phone,
                area,
                address,
                if (isDefault) {
                    "1"
                } else {
                    "0"
                }
            )
    }
}