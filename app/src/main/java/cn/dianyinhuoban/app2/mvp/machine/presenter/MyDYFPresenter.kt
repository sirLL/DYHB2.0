package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.MyMachineDYFBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyDYFContract
import cn.dianyinhuoban.app2.mvp.machine.model.MyDYFModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class MyDYFPresenter(view: MyDYFContract.View) :
    BasePresenter<MyDYFContract.Model, MyDYFContract.View>(view), MyDYFContract.Presenter {
    override fun buildModel(): MyDYFContract.Model {
        return MyDYFModel()
    }

    override fun fetchMyDYF(page: Int, pageSize: Int, type: String) {
        if (!isDestroy) {
            mModel?.let {
                addDispose(
                    it.fetchMyDYF(page, pageSize, type)
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<MyMachineDYFBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: MyMachineDYFBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.bindMyDYF(t)
                                }
                            }
                        })
                )
            }
        }
    }
}