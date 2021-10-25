package cn.dianyinhuoban.app2.mvp.me.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import cn.dianyinhuoban.app2.R
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class AddressActivity : BaseActivity<IPresenter?>() {
    companion object {
        fun openAddressPicker(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, AddressActivity::class.java)
            val bundle = Bundle()
            bundle.putBoolean("isPicker", true)
            intent.putExtras(bundle)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, AddressFragment.newInstance(), "AddressFragment").commit()
        setTitle("收货地址")

        findViewById<Button>(R.id.btn_submit).setOnClickListener {
            AddressEditActivity.openAddressEditActivity(this)
        }
    }

    override fun getPresenter(): IPresenter? {
        return null
    }
}