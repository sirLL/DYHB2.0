package cn.dianyinhuoban.app2.mvp.machine.contract

import cn.dianyinhuoban.app2.bean.MachineTransferRecordBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface MachineTransferRecordContract {
    interface Model : IModel {
        fun fetchTransferRecord(
            page: Int,
            pageSize: Int
        ): Observable<Response<MachineTransferRecordBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchTransferRecord(
            page: Int,
            pageSize: Int
        )
    }

    interface View : IView {
        fun bindTransferRecord(data: MachineTransferRecordBean?)
    }
}