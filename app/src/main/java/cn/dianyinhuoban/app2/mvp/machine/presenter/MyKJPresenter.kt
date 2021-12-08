package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.bean.MyMachineKJBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyKJContract
import cn.dianyinhuoban.app2.mvp.machine.model.MyKJModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class MyKJPresenter(view: MyKJContract.View) :
    BasePresenter<MyKJContract.Model, MyKJContract.View>(view), MyKJContract.Presenter {
    override fun buildModel(): MyKJContract.Model {
        return MyKJModel()
    }

    override fun fetchMyMachineKJ(page: Int, pageSize: Int) {
        if (!isDestroy) {
            mModel?.let {
                addDispose(
                    it.fetchMyMachineKJ(page, pageSize)
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<MyMachineKJBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: MyMachineKJBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.bindMyMachineKJ(t)
                                }
                            }
                        })
                )
            }
        }
    }

    override fun submitTransfer(num: String, underID: String) {
        if (!isDestroy) {
            mModel?.let {
                view?.showLoading()
                addDispose(
                    it.submitTransfer(num, underID)
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<EmptyBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: EmptyBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.onTransferSuccess()
                                }
                            }
                        })
                )
            }
        }
    }
}