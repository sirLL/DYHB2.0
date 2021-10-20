package cn.dianyinhuoban.app2.mvp.home.contract

import cn.dianyinhuoban.app2.bean.BannerBean
import cn.dianyinhuoban.app2.bean.HomeMenuBean
import cn.dianyinhuoban.app2.bean.HomeMenuItemBean
import cn.dianyinhuoban.app2.bean.UserInfoBean
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.mvp.IView
import com.wareroom.lib_http.response.Response
import io.reactivex.Observable

interface HomeContract {
    interface Model {
        fun fetchTopBanner(): Observable<Response<BannerBean?>>

        fun fetchUserInfo(): Observable<Response<UserInfoBean?>>

        fun fetchMenu(): Observable<Response<HomeMenuBean?>>
    }

    interface Presenter : IPresenter {
        fun fetchTopBanner()

        fun fetchUserInfo()

        fun fetchMenu()
    }

    interface View : IView {
        fun bindTopBanner(bannerBean: BannerBean?)

        fun bindUserInfoBean(userInfoBean: UserInfoBean?)

        fun bindMenu(data: List<HomeMenuItemBean?>?)
    }
}