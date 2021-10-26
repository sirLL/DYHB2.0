package cn.dianyinhuoban.app2.mvp.home.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.ShopAppBean
import cn.dianyinhuoban.app2.mvp.home.contract.ShopAppContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class ShopAppModel : BaseModel(), ShopAppContract.Model {
    override fun fetchShopApp(): Observable<Response<ShopAppBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchShopApp(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }
}