package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.MachineTransferRecordBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MachineTransferRecordContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class MachineTransferRecordModel : BaseModel(), MachineTransferRecordContract.Model {
    override fun fetchTransferRecord(
        page: Int,
        pageSize: Int
    ): Observable<Response<MachineTransferRecordBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchTransferRecord(MMKVUtil.getUserID(), MMKVUtil.getToken(), page, pageSize)
    }
}