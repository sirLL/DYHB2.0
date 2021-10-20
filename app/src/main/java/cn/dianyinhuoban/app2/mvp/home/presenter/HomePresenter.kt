package cn.dianyinhuoban.app2.mvp.home.presenter

import cn.dianyinhuoban.app2.bean.BannerBean
import cn.dianyinhuoban.app2.bean.HomeMenuBean
import cn.dianyinhuoban.app2.bean.UserInfoBean
import cn.dianyinhuoban.app2.mvp.home.contract.HomeContract
import cn.dianyinhuoban.app2.mvp.home.model.HomeModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class HomePresenter(view: HomeContract.View) : BasePresenter<HomeModel, HomeContract.View>(view),
    HomeContract.Presenter {
    override fun buildModel(): HomeModel {
        return HomeModel()
    }

    override fun fetchTopBanner() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchTopBanner()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<BannerBean?>() {
                            override fun onError(exception: ApiException?) {
                                handleError(exception)
                            }

                            override fun onNext(t: BannerBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.bindTopBanner(t)
                                }
                            }

                        })
                )
            }
        }
    }

    override fun fetchUserInfo() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchUserInfo()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<UserInfoBean?>() {
                            override fun onError(exception: ApiException?) {
                                handleError(exception)
                            }

                            override fun onNext(t: UserInfoBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.bindUserInfoBean(t)
                                }
                            }
                        })
                )
            }
        }
    }

    override fun fetchMenu() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchMenu()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<HomeMenuBean?>() {
                            override fun onError(exception: ApiException?) {
                                handleError(exception)
                            }

                            override fun onNext(t: HomeMenuBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.bindMenu(t.list)
                                }
                            }
                        })
                )
            }
        }
    }
}