package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.ExchangeRecordBean
import cn.dianyinhuoban.app2.bean.ExchangeRecordItemBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeRecordContract
import cn.dianyinhuoban.app2.mvp.machine.model.ExchangeRecordModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class ExchangeRecordPresenter(view: ExchangeRecordContract.View) :
    BasePresenter<ExchangeRecordContract.Model, ExchangeRecordContract.View>(view),
    ExchangeRecordContract.Presenter {
    override fun buildModel(): ExchangeRecordContract.Model {
        return ExchangeRecordModel()
    }

    override fun fetchExchangeRecord(page: Int, pageSize: Int) {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                it.fetchExchangeRecord(page, pageSize)
                    .compose(SchedulerProvider.getInstance().applySchedulers())
                    .compose(ResponseTransformer.handleResult())
                    .subscribeWith(object : CustomResourceSubscriber<ExchangeRecordBean?>() {
                        override fun onError(exception: ApiException?) {
                            view?.hideLoading()
                            handleError(exception)
                        }

                        override fun onNext(t: ExchangeRecordBean) {
                            super.onNext(t)
                            if (!isDisposed) {
                                val recordData = mutableListOf<ExchangeRecordItemBean>()
                                t.list?.let { data ->
                                    for (item in data) {
                                        recordData.add(item)
                                    }
                                }
                                view?.hideLoading()
                                view?.bindExchangeRecord(recordData)
                            }
                        }
                    })
            }
        }
    }
}