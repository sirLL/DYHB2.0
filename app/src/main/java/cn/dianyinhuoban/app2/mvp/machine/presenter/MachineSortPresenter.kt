package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.MachineSortBean
import cn.dianyinhuoban.app2.bean.MachineSortItemBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MachineSortContract
import cn.dianyinhuoban.app2.mvp.machine.model.MachineSortModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class MachineSortPresenter(view: MachineSortContract.View) :
    BasePresenter<MachineSortContract.Model, MachineSortContract.View>(view),
    MachineSortContract.Presenter {
    override fun buildModel(): MachineSortContract.Model {
        return MachineSortModel()
    }

    override fun fetchSort() {
        if (!isDestroy) {
            view?.showLoading()
            mModel?.let {
                addDispose(
                    it.fetchSort()
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object : CustomResourceSubscriber<MachineSortBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: MachineSortBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.hideLoading()
                                    val data = mutableListOf<MachineSortItemBean>()
                                    t.list?.let { itemList ->
                                        if (itemList.isNotEmpty()) {
                                            for (itemBean in itemList) {
                                                itemBean?.let { item ->
                                                    data.add(item)
                                                }
                                            }
                                        }
                                    }
                                    view?.bindSort(data)
                                }
                            }
                        })
                )
            }
        }
    }
}