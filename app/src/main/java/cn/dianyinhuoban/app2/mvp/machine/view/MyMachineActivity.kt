package cn.dianyinhuoban.app2.mvp.machine.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import cn.dianyinhuoban.app2.R
import com.dy.tablayout.CommonTabLayout
import com.dy.tablayout.listener.CustomTabEntity
import com.dy.tablayout.listener.TabEntity
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class MyMachineActivity : BaseActivity<IPresenter?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_machine)
        setTitle("我的设备")
        setRightButtonText("划拔记录") {
            startActivity(Intent(this, MachineTransferRecordActivity::class.java))
        }
        setupTabLayout()
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    private fun setupTabLayout() {
        val tabLayout = findViewById<CommonTabLayout>(R.id.tab_layout)
        val tabList = ArrayList<CustomTabEntity>()
        tabList.add(TabEntity("电银POS"))
        tabList.add(TabEntity("电银付"))
        tabList.add(TabEntity("快捷"))
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(MyMachinePOSFragment.newInstance())
        fragmentList.add(MyMachineDYFFragment.newInstance())
        fragmentList.add(MyMachineKJFragment.newInstance())
        tabLayout.setTabData(tabList, supportFragmentManager, R.id.fl_container, fragmentList)
    }

}