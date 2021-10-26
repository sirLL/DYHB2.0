package cn.dianyinhuoban.app2.mvp.home.contract

import cn.dianyinhuoban.app2.bean.ShopAppBean
import cn.dianyinhuoban.app2.bean.ShopAppItemBean
import com.wareroom.lib_base.mvp.IModel
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface ShopAppContract {
    interface Model : IModel {
        fun fetchShopApp(): Observable<Response<ShopAppBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchShopApp()
    }

    interface View : IView {
        fun bindShopApp(data: List<ShopAppItemBean>?)
    }
}