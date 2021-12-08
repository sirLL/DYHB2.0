package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import cn.dianyinhuoban.app2.R
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class MachineTransferRecordActivity : BaseActivity<IPresenter?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_machine_transfer_record)
        setTitle("划分记录")
        supportFragmentManager.beginTransaction().add(
            R.id.fl_container,
            MachineTransferRecordFragment.newInstance(),
            "MachineTransferRecordFragment"
        ).commit()
    }

    override fun getPresenter(): IPresenter? {
        return null
    }
}