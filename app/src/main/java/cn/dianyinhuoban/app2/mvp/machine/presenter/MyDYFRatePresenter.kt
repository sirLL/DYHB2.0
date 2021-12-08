package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.MyMachineDYFRateBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyDYFRateContract
import cn.dianyinhuoban.app2.mvp.machine.model.MyDYFRateModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class MyDYFRatePresenter(view: MyDYFRateContract.View) :
    BasePresenter<MyDYFRateContract.Model, MyDYFRateContract.View>(view),
    MyDYFRateContract.Presenter {
    override fun buildModel(): MyDYFRateContract.Model {
        return MyDYFRateModel()
    }

    override fun fetchMyDYFRate(rateID: String) {
        if (!isDestroy) {
            mModel?.let {
                view?.showLoading()
                addDispose(
                    it.fetchMyDYFRate(rateID)
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<MyMachineDYFRateBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: MyMachineDYFRateBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.bindMyDYFRate(t)
                                }
                            }
                        })
                )
            }
        }
    }
}
