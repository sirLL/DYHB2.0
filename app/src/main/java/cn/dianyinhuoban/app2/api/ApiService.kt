package cn.dianyinhuoban.app2.api

import cn.dianyinhuoban.app2.bean.*
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("v4/partner/login")
    fun submitLogin(
        @Field("telephone") phone: String,
        @Field("password") password: String
    ): Observable<Response<LoginInfoBean?>>

    @FormUrlEncoded
    @POST("v4/system/banner")
    fun fetchTopBanner(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<BannerBean?>>

    @FormUrlEncoded
    @POST("v4/Partner/index")
    fun fetchUserInfo(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<UserInfoBean?>>

    @FormUrlEncoded
    @POST("v4/bill/my_income")
    fun fetchIncome(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<IncomeBean?>>

    @FormUrlEncoded
    @POST("v4/Activity/integral")
    fun fetchIntegral(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<IntegralBean?>>

    @FormUrlEncoded
    @POST("v4/system/navList")
    fun fetchHomeMenu(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<HomeMenuBean?>>
}