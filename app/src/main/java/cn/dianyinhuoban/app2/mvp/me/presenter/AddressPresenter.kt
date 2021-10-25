package cn.dianyinhuoban.app2.mvp.me.presenter

import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.mvp.me.contract.AddressContract
import cn.dianyinhuoban.app2.mvp.me.model.AddressModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class AddressPresenter(view: AddressContract.View) :
    BasePresenter<AddressContract.Model, AddressContract.View>(view), AddressContract.Presenter {
    override fun buildModel(): AddressContract.Model {
        return AddressModel()
    }

    override fun fetchAddress() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
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
                )
            }
        }
    }

    override fun submitDefAddress(addressID: String) {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.submitDefAddress(addressID)
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
                                    view?.onSubmitDefAddressSuccess(addressID)
                                }
                            }
                        })
                )
            }
        }
    }

    override fun deleteAddress(addressID: String) {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.deleteAddress(addressID)
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
                                    view?.onDeleteAddressSuccess(addressID)
                                }
                            }
                        })
                )
            }
        }
    }
}