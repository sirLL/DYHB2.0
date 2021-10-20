package cn.dianyinhuoban.app2.mvp.wallet.presenter

import cn.dianyinhuoban.app2.bean.IncomeBean
import cn.dianyinhuoban.app2.bean.IntegralBean
import cn.dianyinhuoban.app2.mvp.wallet.contract.IncomeContract
import cn.dianyinhuoban.app2.mvp.wallet.model.IncomeModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class IncomePresenter(view: IncomeContract.View) :
    BasePresenter<IncomeContract.Model, IncomeContract.View>(view), IncomeContract.Presenter {
    override fun buildModel(): IncomeContract.Model {
        return IncomeModel()
    }

    override fun fetchIncome() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchIncome()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<IncomeBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: IncomeBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.bindIncome(t)
                                }
                            }
                        })
                )
            }
        }
    }

    override fun fetchIntegral() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchIntegral()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<IntegralBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: IntegralBean) {
                                super.onNext(t)
                                if (!isDestroy) {
                                    view?.hideLoading()
                                    view?.bindIntegral(t)
                                }
                            }
                        })
                )
            }
        }
    }
}