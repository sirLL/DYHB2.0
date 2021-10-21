package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.AddressItemBean
import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.ExchangePresenter
import com.wareroom.lib_base.ui.BaseActivity

class ExchangeActivity : BaseActivity<ExchangeContract.Presenter?>(), ExchangeContract.View {
    private var tvName: TextView? = null
    private var tvPhone: TextView? = null
    private var tvAddress: TextView? = null
    private var tvAddressHint: TextView? = null
    private var tvTitle: TextView? = null
    private var tvPrice: TextView? = null
    private var tvIntegralCP: TextView? = null
    private var tvIntegralDQ: TextView? = null
    private var tvCount: TextView? = null
    private var ivSubtract: ImageView? = null
    private var ivAdd: ImageView? = null
    private var btnSubmit: Button? = null

    override fun getPresenter(): ExchangeContract.Presenter? {
        return ExchangePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)
        initView()
        fetchAddress()
        fetchExchangeMachine()
    }

    private fun initView() {
        tvName = findViewById(R.id.tv_name)
        tvPhone = findViewById(R.id.tv_phone)
        tvAddress = findViewById(R.id.tv_address)
        tvAddressHint = findViewById(R.id.tv_hint)
    }

    private fun fetchAddress() {
        mPresenter?.fetchAddress()
    }

    override fun bindAddress(addressBean: AddressBean?) {
        addressBean?.arylist?.let {
            for (itemBean in it) {
                if ("1" == itemBean.isdefault) {
                    bindSelectedAddress(itemBean)
                    break
                }
            }
        }
    }

    private fun bindSelectedAddress(addressItemBean: AddressItemBean?) {
        if (addressItemBean == null) {
            tvAddressHint?.visibility = View.VISIBLE
            tvName?.visibility = View.GONE
            tvPhone?.visibility = View.GONE
            tvAddress?.visibility = View.GONE
        } else {
            tvAddressHint?.visibility = View.GONE
            tvName?.visibility = View.VISIBLE
            tvPhone?.visibility = View.VISIBLE
            tvAddress?.visibility = View.VISIBLE

            tvName?.text = addressItemBean.contact
            tvPhone?.text = addressItemBean.mobile
            tvAddress?.text = addressItemBean.area + addressItemBean.address
        }
    }

    private fun fetchExchangeMachine() {
        mPresenter?.fetchExchangeMachine(0, 20)
    }

    override fun bindExchangeMachine(machineBean: ExchangeMachineBean?) {

    }

}