package cn.dianyinhuoban.app2.mvp.me.contract

import cn.dianyinhuoban.app2.bean.AddressItemBean
import cn.dianyinhuoban.app2.bean.EmptyBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface AddressEditContract {
    interface Model : IModel {
        fun submitAddress(
            addressID: String,
            name: String,
            phone: String,
            area: String,
            address: String,
            isDefault: Boolean
        ): Observable<Response<EmptyBean?>>
    }

    interface Presenter : IPresenter {
        fun submitAddress(
            addressID: String,
            name: String,
            phone: String,
            area: String,
            address: String,
            isDefault: Boolean
        )
    }

    interface View : IView {
        fun onSubmitAddressSuccess(address: AddressItemBean)
    }
}