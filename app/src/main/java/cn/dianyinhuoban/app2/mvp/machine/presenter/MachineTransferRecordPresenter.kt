package cn.dianyinhuoban.app2.mvp.machine.presenter

import cn.dianyinhuoban.app2.bean.MachineTransferRecordBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MachineTransferRecordContract
import cn.dianyinhuoban.app2.mvp.machine.model.MachineTransferRecordModel
import com.wareroom.lib_base.mvp.BasePresenter
import com.wareroom.lib_http.CustomResourceSubscriber
import com.wareroom.lib_http.exception.ApiException
import com.wareroom.lib_http.response.ResponseTransformer
import com.wareroom.lib_http.schedulers.SchedulerProvider

class MachineTransferRecordPresenter(view: MachineTransferRecordContract.View) :
    BasePresenter<MachineTransferRecordContract.Model, MachineTransferRecordContract.View>(view),
    MachineTransferRecordContract.Presenter {
    override fun buildModel(): MachineTransferRecordContract.Model {
        return MachineTransferRecordModel()
    }

    override fun fetchTransferRecord(page: Int, pageSize: Int) {
        if (!isDestroy) {
            mModel?.let {
                addDispose(
                    it.fetchTransferRecord(page, pageSize)
                        .compose(SchedulerProvider.getInstance().applySchedulers())
                        .compose(ResponseTransformer.handleResult())
                        .subscribeWith(object :
                            CustomResourceSubscriber<MachineTransferRecordBean?>() {
                            override fun onError(exception: ApiException?) {
                                view?.hideLoading()
                                handleError(exception)
                            }

                            override fun onNext(t: MachineTransferRecordBean) {
                                super.onNext(t)
                                if (!isDisposed) {
                                    view?.bindTransferRecord(t)
                                }
                            }
                        })
                )
            }
        }
    }
}
