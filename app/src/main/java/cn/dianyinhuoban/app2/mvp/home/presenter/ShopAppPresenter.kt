package cn.dianyinhuoban.app2.mvp.home.presenter

import cn.dianyinhuoban.app2.bean.BannerBean
import cn.dianyinhuoban.app2.bean.ShopAppBean
import cn.dianyinhuoban.app2.bean.ShopAppItemBean
import cn.dianyinhuoban.app2.mvp.home.contract.ShopAppContract
import cn.dianyinhuoban.app2.mvp.home.model.ShopAppModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class ShopAppPresenter(view: ShopAppContract.View) :
    BasePresenter<ShopAppContract.Model, ShopAppContract.View>(view), ShopAppContract.Presenter {
    override fun buildModel(): ShopAppContract.Model {
        return ShopAppModel()
    }

    override fun fetchShopApp() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchShopApp()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<ShopAppBean?>() {
                            override fun onError(exception: ApiException?) {
                                handleError(exception)
                            }

                            override fun onNext(t: ShopAppBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    val data = mutableListOf<ShopAppItemBean>()
                                    t.list?.let { appList ->
                                        for (app in appList) {
                                            data.add(app)
                                        }
                                    }
                                    view?.hideLoading()
                                    view?.bindShopApp(data)
                                }
                            }

                        })
                )
            }
        }
    }
}