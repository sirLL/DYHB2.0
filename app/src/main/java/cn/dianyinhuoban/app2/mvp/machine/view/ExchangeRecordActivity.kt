package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import cn.dianyinhuoban.app2.R
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class ExchangeRecordActivity : BaseActivity<IPresenter?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_record)
        setTitle("兑换记录")
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, ExchangeRecordFragment.newInstance(), "ExchangeRecordFragment")
            .commit()
    }

    override fun getPresenter(): IPresenter? {
        return null
    }
}