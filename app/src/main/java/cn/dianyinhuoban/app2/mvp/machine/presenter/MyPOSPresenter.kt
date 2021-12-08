package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.MyMachinePOSBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyPOSContract
import cn.dianyinhuoban.app2.mvp.machine.model.MyPOSModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class MyPOSPresenter(view: MyPOSContract.View) :
    BasePresenter<MyPOSContract.Model, MyPOSContract.View>(view), MyPOSContract.Presenter {
    override fun buildModel(): MyPOSContract.Model {
        return MyPOSModel()
    }

    override fun fetchMyPOS(page: Int, pageSize: Int, keyword: String, type: String) {
        if (!isDestroy) {
            mModel?.let {
                addDispose(
                    it.fetchMyPOS(page, pageSize, keyword, type)
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<MyMachinePOSBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: MyMachinePOSBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.bindMyPos(t)
                                }
                            }
                        })
                )
            }
        }
    }
}