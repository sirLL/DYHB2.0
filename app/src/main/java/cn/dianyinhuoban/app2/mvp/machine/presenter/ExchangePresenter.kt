package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeContract
import cn.dianyinhuoban.app2.mvp.machine.model.ExchangeModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class ExchangePresenter(view: ExchangeContract.View) :
    BasePresenter<ExchangeContract.Model, ExchangeContract.View>(view), ExchangeContract.Presenter {
    override fun buildModel(): ExchangeContract.Model {
        return ExchangeModel()
    }

    override fun fetchAddress() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                it.fetchAddress()
                    .compose(SchedulerProvider.getInstance().applySchedulers())
                    .compose(ResponseTransformer.handleResult())
                    .subscribeWith(object : CustomResourceSubscriber<AddressBean?>() {
                        override fun onError(exception: ApiException?) {
                            view?.hideLoading()
                            handleError(exception)
                        }

                        override fun onNext(t: AddressBean) {
                            super.onNext(t)
                            if (!isDisposed) {
                                view?.hideLoading()
                                view?.bindAddress(t)
                            }
                        }
                    })
            }
        }
    }

    override fun fetchExchangeMachine(page: Int, pageSize: Int) {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                it.fetchExchangeMachine(page, pageSize)
                    .compose(SchedulerProvider.getInstance().applySchedulers())
                    .compose(ResponseTransformer.handleResult())
                    .subscribeWith(object : CustomResourceSubscriber<ExchangeMachineBean?>() {
                        override fun onError(exception: ApiException?) {
                            view?.hideLoading()
                            handleError(exception)
                        }

                        override fun onNext(t: ExchangeMachineBean) {
                            super.onNext(t)
                            if (!isDisposed) {
                                view?.hideLoading()
                                view?.bindExchangeMachine(t)
                            }
                        }
                    })
            }
        }
    }
}