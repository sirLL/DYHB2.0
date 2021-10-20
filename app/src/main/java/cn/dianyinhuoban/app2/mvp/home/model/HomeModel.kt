package cn.dianyinhuoban.app2.mvp.home.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.BannerBean
import cn.dianyinhuoban.app2.bean.HomeMenuBean
import cn.dianyinhuoban.app2.bean.UserInfoBean
import cn.dianyinhuoban.app2.mvp.home.contract.HomeContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class HomeModel : BaseModel(), HomeContract.Model {
    override fun fetchTopBanner(): Observable<Response<BannerBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchTopBanner(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }

    override fun fetchUserInfo(): Observable<Response<UserInfoBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchUserInfo(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }

    override fun fetchMenu(): Observable<Response<HomeMenuBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchHomeMenu(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }
}