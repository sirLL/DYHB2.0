package cn.dianyinhuoban.app2.mvp.login.modelview

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.LoginInfoBean
import cn.dianyinhuoban.app2.mvp.login.contract.LoginContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class LoginModel : BaseModel(), LoginContract.Model {
    override fun submitLogin(
        phone: String,
        password: String
    ): Observable<Response<LoginInfoBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .submitLogin(phone, password)
    }
}