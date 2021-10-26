package cn.dianyinhuoban.app2.mvp.home.view

import android.os.Bundle
import cn.dianyinhuoban.app2.R
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class ShopCodeActivity : BaseActivity<IPresenter?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_code)
        setTitle("商户APP")
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, ShopCodeFragment.newInstance(), "ShopCodeFragment").commit()
    }

    override fun getPresenter(): IPresenter? {
        return null
    }
}