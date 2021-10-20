package cn.dianyinhuoban.app2.mvp.login.presenter

import cn.dianyinhuoban.app2.PWDTools
import cn.dianyinhuoban.app2.bean.LoginInfoBean
import cn.dianyinhuoban.app2.mvp.login.contract.LoginContract
import cn.dianyinhuoban.app2.mvp.login.modelview.LoginModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class LoginPresenter(view: LoginContract.View) :
    BasePresenter<LoginModel, LoginContract.View>(view), LoginContract.Presenter {
    override fun buildModel(): LoginModel {
        return LoginModel()
    }

    override fun submitLogin(phone: String, password: String) {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.submitLogin(phone, PWDTools.encryptPassword(password))
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<LoginInfoBean?>() {
                            override fun onError(exception: ApiException?) {
                                handleError(exception)
                            }

                            override fun onNext(t: LoginInfoBean) {
                                super.onNext(t)
                                handLoginInfo(t, phone, password)
                            }
                        })
                )
            }
        }
    }

    private fun handLoginInfo(infoBean: LoginInfoBean, phone: String, password: String) {
        MMKVUtil.savePhone(phone)
        MMKVUtil.saveLoginPassword(password)
        MMKVUtil.saveUserName(phone)
        MMKVUtil.saveToken(infoBean.token)
        MMKVUtil.saveUserID(infoBean.id)
        if (!isDestroy) {
            view?.let {
                it.onLoginSuccess()
            }
        }
    }
}