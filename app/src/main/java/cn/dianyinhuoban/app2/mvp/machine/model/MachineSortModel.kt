package cn.dianyinhuoban.app2.mvp.machine.model

import cn.dianyinhuoban.app2.api.ApiService
import cn.dianyinhuoban.app2.bean.MachineSortBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MachineSortContract
import com.wareroom.lib_base.mvp.BaseModel
import com.wareroom.lib_base.utils.cache.MMKVUtil
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

class MachineSortModel : BaseModel(), MachineSortContract.Model {
    override fun fetchSort(): Observable<Response<MachineSortBean?>> {
        return mRetrofit.create(ApiService::class.java)
            .fetchMachineSort(MMKVUtil.getUserID(), MMKVUtil.getToken())
    }
}