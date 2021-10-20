package cn.dianyinhuoban.app2.mvp.login.contract

import cn.dianyinhuoban.app2.bean.LoginInfoBean
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface LoginContract {
    interface Model {
        fun submitLogin(phone: String, password: String): Observable<Response<LoginInfoBean?>>
    }

    interface Presenter {
        fun submitLogin(phone: String, password: String)
    }

    interface View : IView {
        fun onLoginSuccess()

        fun onLoginError()
    }

}