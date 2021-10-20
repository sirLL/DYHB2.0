package cn.dianyinhuoban.app2.mvp.home.presenter

import cn.dianyinhuoban.app2.mvp.home.contract.SystemContract
import cn.dianyinhuoban.app2.mvp.home.model.SystemModel
import com.wareroom.lib_base.mvp.BasePresenter

class SystemPresenter(view: SystemContract.View) :
    BasePresenter<SystemModel, SystemContract.View>(view), SystemContract.Presenter {
    override fun buildModel(): SystemModel {
        return SystemModel()
    }
}