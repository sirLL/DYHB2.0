package cn.dianyinhuoban.app2.mvp.machine.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.dianyinhuoban.app2.R
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class ExchangeMachinePickerActivity : BaseActivity<IPresenter?>() {
    companion object {
        fun openExchangeMachinePicker(activity: Activity, selectedID: String?, requestCode: Int) {
            val intent = Intent(activity, ExchangeMachinePickerActivity::class.java)
            val bundle = Bundle()
            bundle.putString("selectedID", selectedID)
            intent.putExtras(bundle)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_machine_picker)
        setTitle("机具选择")
        val selectedID = intent.extras?.getString("selectedID")

        supportFragmentManager.beginTransaction().add(
            R.id.fl_container,
            ExchangeMachinePickerFragment.newInstance(selectedID),
            "ExchangeMachinePickerFragment"
        ).commit()
    }

    override fun getPresenter(): IPresenter? {
        return null
    }
}