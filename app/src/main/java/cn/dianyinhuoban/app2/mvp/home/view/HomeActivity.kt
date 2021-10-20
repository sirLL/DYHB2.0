package cn.dianyinhuoban.app2.mvp.home.view

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.mvp.home.contract.SystemContract
import cn.dianyinhuoban.app2.mvp.home.presenter.SystemPresenter
import cn.dianyinhuoban.app2.mvp.me.view.MeFragment
import cn.dianyinhuoban.app2.mvp.wallet.view.IncomeFragment
import com.gyf.immersionbar.ImmersionBar
import com.wareroom.lib_base.ui.BaseActivity
import com.wareroom.lib_base.widget.bottomnavigation.ShowHiddenNavigation

class HomeActivity : BaseActivity<SystemPresenter>(), SystemContract.View {
    private var navigationBar: ShowHiddenNavigation? = null


    override fun toolbarIsEnable(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigationBar = findViewById(R.id.navigation_bar)
        setupNavigationBar()
    }

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .statusBarColor(statusBarColor)
            .autoDarkModeEnable(isDarkModeEnable)
            .autoStatusBarDarkModeEnable(isDarkModeEnable)
            .statusBarDarkFont(true)
            .fitsSystemWindows(false)
            .flymeOSStatusBarFontColor(com.wareroom.lib_base.R.color.black)
            .reset()
            .init()
    }

    override fun getPresenter(): SystemPresenter {
        return SystemPresenter(this)
    }

    private fun setupNavigationBar() {
        navigationBar?.let {
            it.addTab(
                R.drawable.ic_nav_home_normal,
                R.drawable.ic_nav_home_checked,
                ContextCompat.getColor(this, R.color.color_454444),
                ContextCompat.getColor(this, R.color.color_0089fd),
                "首页"
            )
            it.addTab(
                R.drawable.ic_nav_income_normal,
                R.drawable.ic_nav_income_checked,
                ContextCompat.getColor(this, R.color.color_454444),
                ContextCompat.getColor(this, R.color.color_0089fd),
                "收益"
            )
            it.addTab(
                R.drawable.ic_nav_receive_payment_normal,
                R.drawable.ic_nav_receive_payment_checked,
                ContextCompat.getColor(this, R.color.color_454444),
                ContextCompat.getColor(this, R.color.color_0089fd),
                "收款"
            )
            it.addTab(
                R.drawable.ic_nav_share_normal,
                R.drawable.ic_nav_share_checked,
                ContextCompat.getColor(this, R.color.color_454444),
                ContextCompat.getColor(this, R.color.color_0089fd),
                "分享"
            )
            it.addTab(
                R.drawable.ic_nav_mine_normal,
                R.drawable.ic_nav_mine_checked,
                ContextCompat.getColor(this, R.color.color_454444),
                ContextCompat.getColor(this, R.color.color_0089fd),
                "我的"
            )
            val fragmentList = mutableListOf<Fragment>()
            fragmentList.add(HomeFragment.newInstance())
            fragmentList.add(IncomeFragment.newInstance())
            fragmentList.add(MeFragment.newInstance())
            it.setupFragments(supportFragmentManager, R.id.fl_container, fragmentList)
        }
    }
}