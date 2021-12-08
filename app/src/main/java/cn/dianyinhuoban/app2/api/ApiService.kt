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

    @FormUrlEncoded
    @POST("v4/Product/goodsType")
    fun fetchMachineSort(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<MachineSortBean?>>

    @FormUrlEncoded
    @POST("v4/partner/index")
    fun fetchMerchant(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<UserInfoBean?>>

    @FormUrlEncoded
    @POST("v4/Product/index")
    fun fetchMachine(
        @Field("id") id: String,
        @Field("token") token: String,
        @Field("type") type: String
    ): Observable<Response<MachineBean?>>

    @FormUrlEncoded
    @POST("v4/partner/address")
    fun fetchAddress(
        @Field("id") id: String,
        @Field("token") token: String
    ): Observable<Response<AddressBean?>>

    @FormUrlEncoded
    @POST("v4/Activity/goodsList")
    fun fetchExchangeMachine(
        @Field("id") id: String,
        @Field("token") token: String,
        @Field("page") page: Int,
        @Field("pagesize") pageSize: Int
    ): Observable<Response<ExchangeMachineBean?>>

    @FormUrlEncoded
    @POST("v4/partner/addressdefault")
    fun submitDefAddress(
        @Field("id") id: String,
        @Field("token") token: String,
        @Field("addressid") addressID: String
    ): Observable<Response<EmptyBean?>>

    @FormUrlEncoded
    @POST("v4/partner/addressdelete")
    fun deleteAddress(
        @Field("id") id: String,
        @Field("token") token: String,
        @Field("addressid") addressID: String
    ): Observable<Response<EmptyBean?>>

    @FormUrlEncoded
    @POST("v4/partner/addressmodify")
    fun submitAddress(
        @Field("id") id: String,
        @Field("token") token: String,
        @Field("addressid") addressID: String,
        @Field("contact") name: String,
        @Field("mobile") phone: String,
        @Field("area") area: String,
        @Field("address") address: String,
        @Field("isdefault") isDefault: String
    ): Observable<Response<EmptyBean?>>

    @FormUrlEncoded
    @POST("v4/Activity/itgExchange")
    fun submitExchangeMachine(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("exchange") num: String,
        @Field("type") type: String,
        @Field("contact") name: String,
        @Field("telephone") phone: String,
        @Field("address") address: String,
        @Field("password") password: String
    ): Observable<Response<List<EmptyBean?>?>>

    @FormUrlEncoded
    @POST("v4/Activity/exchangeList")
    fun fetchExchangeRecord(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("page") page: Int,
        @Field("pagesize") pageSize: Int
    ): Observable<Response<ExchangeRecordBean?>>

    @FormUrlEncoded
    @POST("v4/system/appList")
    fun fetchShopApp(
        @Field("id") uid: String,
        @Field("token") token: String
    ): Observable<Response<ShopAppBean?>>

    /**
     * @param type 1未激活，2已激活，3未绑定客户电话，4已绑定客户电话
     */
    @FormUrlEncoded
    @POST("v4/act/index")
    fun fetchMyMachinePOS(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("page") page: Int,
        @Field("pagesize") pageSize: Int,
        @Field("keyword") keyword: String,
        @Field("type") type: String
    ): Observable<Response<MyMachinePOSBean?>>

    @FormUrlEncoded
    @POST("v4/code/code_list")
    fun fetchMyMachineDYF(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("page") page: Int,
        @Field("pagesize") pageSize: Int,
        @Field("type") type: String
    ): Observable<Response<MyMachineDYFBean?>>

    @FormUrlEncoded
    @POST("v4/code/rate_info")
    fun fetchMyDYFRate(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("rateid") rateID: String
    ): Observable<Response<MyMachineDYFRateBean?>>

    @FormUrlEncoded
    @POST("v4/Product/vfMemberList")
    fun fetchMyMachineKJ(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("page") page: Int,
        @Field("pagesize") pageSize: Int
    ): Observable<Response<MyMachineKJBean?>>

    @FormUrlEncoded
    @POST("v4/Product/transfer")
    fun submitTransfer(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("type") type: String,
        @Field("num") num: String,
        @Field("underid") underid: String
    ): Observable<Response<EmptyBean?>>

    @FormUrlEncoded
    @POST("v4/Product/vfTransferList")
    fun fetchTransferRecord(
        @Field("id") uid: String,
        @Field("token") token: String,
        @Field("page") page: Int,
        @Field("pagesize") pageSize: Int
    ): Observable<Response<MachineTransferRecordBean?>>
}