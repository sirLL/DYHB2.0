package cn.dianyinhuoban.app2.mvp.machine.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MachineSortItemBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MachineSortContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.MachineSortPresenter
import com.dy.tablayout.SlidingTabLayout
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class BuyMachineActivity : BaseActivity<MachineSortContract.Presenter?>(),
    MachineSortContract.View {
    private var viewPager: ViewPager? = null
    private var tabLayout: SlidingTabLayout? = null

    override fun getPresenter(): MachineSortContract.Presenter? {
        return return MachineSortPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_machine)
        setTitle("机具领用")
        setRightButtonText("兑换") {
            startActivity(Intent(this, ExchangeActivity::class.java))
        }
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        fetchSort()
    }

    private fun fetchSort() {
        mPresenter?.fetchSort()
    }

    override fun bindSort(data: List<MachineSortItemBean?>?) {
        val fragmentList = arrayListOf<Fragment>()
        val titleArr = arrayListOf<String>()
        data?.let {
            for (itemBean in it) {
                itemBean?.let { item ->
                    if (!TextUtils.isEmpty(item.type) && !TextUtils.isEmpty(item.name)) {
                        fragmentList.add(BuyMachineFragment.newInstance(item.type!!))
                        titleArr.add(item.name!!)
                    }
                }
            }
        }

        tabLayout?.setViewPager(
            viewPager,
            titleArr.toTypedArray(),
            supportFragmentManager,
            fragmentList
        )
    }


}