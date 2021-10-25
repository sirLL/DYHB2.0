package cn.dianyinhuoban.app2.mvp.me.presenter

import cn.dianyinhuoban.app2.bean.AddressItemBean
import cn.dianyinhuoban.app2.bean.EmptyBean
import cn.dianyinhuoban.app2.mvp.me.contract.AddressEditContract
import cn.dianyinhuoban.app2.mvp.me.model.AddressEditModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class AddressEditPresenter(view: AddressEditContract.View) :
    BasePresenter<AddressEditContract.Model, AddressEditContract.View>(view),
    AddressEditContract.Presenter {
    override fun buildModel(): AddressEditContract.Model {
        return AddressEditModel()
    }

    override fun submitAddress(
        addressID: String,
        name: String,
        phone: String,
        area: String,
        address: String,
        isDefault: Boolean
    ) {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.submitAddress(addressID, name, phone, area, address, isDefault)
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
                                    val addressBean = AddressItemBean(
                                        address,
                                        area,
                                        name,
                                        addressID,
                                        if (isDefault) {
                                            "1"
                                        } else {
                                            "0"
                                        },
                                        phone
                                    )

                                    view?.hideLoading()
                                    view?.onSubmitAddressSuccess(addressBean)
                                }
                            }
                        })
                )
            }
        }
    }
}