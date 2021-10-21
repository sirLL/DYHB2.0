package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.MachineBean
import cn.dianyinhuoban.app2.bean.UserInfoBean
import cn.dianyinhuoban.app2.mvp.machine.contract.BuyMachineContract
import cn.dianyinhuoban.app2.mvp.machine.model.BuyMachineModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class BuyMachinePresenter(view: BuyMachineContract.View) :
    BasePresenter<BuyMachineContract.Model, BuyMachineContract.View>(view),
    BuyMachineContract.Presenter {
    override fun buildModel(): BuyMachineContract.Model {
        return BuyMachineModel()
    }

    override fun fetchMerchant() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchMerchant()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<UserInfoBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: UserInfoBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.bindMerchant(t)
                                }
                            }
                        })
                )
            }
        }
    }

    override fun fetchMachine(type: String) {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchMachine(type)
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<MachineBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: MachineBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    view?.bindMachine(t)
                                }
                            }
                        })
                )
            }
        }
    }
}