package cn.dianyinhuoban.app2.mvp.me.contract

import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.EmptyBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface AddressContract {
    interface Model : IModel {
        fun fetchAddress(): Observable<Response<AddressBean?>>

        fun submitDefAddress(addressID: String): Observable<Response<EmptyBean?>>

        fun deleteAddress(addressID: String):Observable<Response<EmptyBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchAddress()

        fun submitDefAddress(addressID: String)

        fun deleteAddress(addressID: String)
    }

    interface View : IView {
        fun bindAddress(addressBean: AddressBean?)

        fun onSubmitDefAddressSuccess(addressID: String)

        fun onDeleteAddressSuccess(addressID: String)
    }
}