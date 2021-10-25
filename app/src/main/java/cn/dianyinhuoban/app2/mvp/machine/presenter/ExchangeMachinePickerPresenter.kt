package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeMachinePickerContract
import cn.dianyinhuoban.app2.mvp.machine.model.ExchangeMachinePickerModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class ExchangeMachinePickerPresenter(view: ExchangeMachinePickerContract.View) :
    BasePresenter<ExchangeMachinePickerContract.Model, ExchangeMachinePickerContract.View>(view),
    ExchangeMachinePickerContract.Presenter {
    override fun buildModel(): ExchangeMachinePickerContract.Model {
        return ExchangeMachinePickerModel()
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