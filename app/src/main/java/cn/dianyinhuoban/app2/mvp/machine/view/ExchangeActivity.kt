package cn.dianyinhuoban.app2.mvp.machine.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.AddressItemBean
import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import cn.dianyinhuoban.app2.bean.ExchangeMachineItemBean
import cn.dianyinhuoban.app2.event.AddressDeleteSuccessEvent
import cn.dianyinhuoban.app2.event.AddressEditSuccessEvent
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.ExchangePresenter
import cn.dianyinhuoban.app2.mvp.me.view.AddressActivity
import cn.dianyinhuoban.app2.widget.dialog.PayPwdDialog
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.wareroom.lib_base.ui.BaseActivity
import com.wareroom.lib_base.utils.NumberUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
    private var ivCover: RoundedImageView? = null

    private var selectedMachine: ExchangeMachineItemBean? = null
    private var selectedAddress: AddressItemBean? = null

    companion object {
        private const val RC_SELECT_MACHINE = 1
        private const val RC_SELECT_ADDRESS = 2
    }

    override fun getPresenter(): ExchangeContract.Presenter? {
        return ExchangePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(R.layout.activity_exchange)
        setTitle("兑换")
        setRightButtonText("兑换记录") {
            startActivity(
                Intent(
                    this,
                    ExchangeRecordActivity::class.java
                )
            )
        }
        initView()
        fetchAddress()
        fetchExchangeMachine()
    }

    private fun initView() {
        tvName = findViewById(R.id.tv_name)
        tvPhone = findViewById(R.id.tv_phone)
        tvAddress = findViewById(R.id.tv_address)
        tvAddressHint = findViewById(R.id.tv_hint)
        tvTitle = findViewById(R.id.tv_title)
        tvPrice = findViewById(R.id.tv_price)
        tvIntegralCP = findViewById(R.id.tv_integral_cp)
        tvIntegralDQ = findViewById(R.id.tv_integral_dq)
        tvCount = findViewById(R.id.tv_count)
        ivSubtract = findViewById(R.id.iv_subtract)
        ivAdd = findViewById(R.id.iv_add)
        btnSubmit = findViewById(R.id.btn_submit)
        ivCover = findViewById(R.id.iv_cover)
        initEvent()
    }

    private fun initEvent() {
        ivAdd?.setOnClickListener {
            val countStr = tvCount?.text?.toString() ?: "1"
            val count = if (countStr.isEmpty()) {
                1
            } else {
                countStr.toInt()
            }
            tvCount?.text = (count + 1).toString()
        }
        ivSubtract?.setOnClickListener {
            val countStr = tvCount?.text?.toString() ?: "1"
            val count = if (countStr.isEmpty()) {
                1
            } else {
                countStr.toInt()
            }
            tvCount?.text = if (count <= 1) {
                "1"
            } else {
                (count - 1).toString()
            }
        }
        tvCount?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                setSubmitEnable()
            }
        })

        findViewById<TextView>(R.id.tv_select_machine).setOnClickListener {
            ExchangeMachinePickerActivity.openExchangeMachinePicker(
                ExchangeActivity@ this, selectedMachine?.id,
                RC_SELECT_MACHINE
            )
        }

        findViewById<ConstraintLayout>(R.id.cl_address_container).setOnClickListener {
            AddressActivity.openAddressPicker(this, RC_SELECT_ADDRESS)
        }
        btnSubmit?.setOnClickListener {
            showPasswordDialog()
        }
    }

    private fun setSubmitEnable() {
        val countStr = tvCount?.text?.toString() ?: "1"
        val count = if (countStr.isEmpty()) {
            0
        } else {
            countStr.toInt()
        }
        btnSubmit?.isEnabled = selectedAddress != null && selectedMachine != null && count > 0
    }

    private fun fetchAddress() {
        mPresenter?.fetchAddress()
    }

    override fun bindAddress(addressBean: AddressBean?) {
        var address: AddressItemBean? = null
        addressBean?.arylist?.let {
            for (itemBean in it) {
                if ("1" == itemBean.isdefault) {
                    address = itemBean
                    break
                }
            }
        }
        bindSelectedAddress(address)
    }

    private fun bindSelectedAddress(addressItemBean: AddressItemBean?) {
        selectedAddress = addressItemBean
        if (selectedAddress == null) {
            tvAddressHint?.visibility = View.VISIBLE
            tvName?.visibility = View.GONE
            tvPhone?.visibility = View.GONE
            tvAddress?.visibility = View.GONE
        } else {
            tvAddressHint?.visibility = View.GONE
            tvName?.visibility = View.VISIBLE
            tvPhone?.visibility = View.VISIBLE
            tvAddress?.visibility = View.VISIBLE

            tvName?.text = selectedAddress?.contact
            tvPhone?.text = selectedAddress?.mobile
            tvAddress?.text = selectedAddress?.area + selectedAddress?.address
        }
        setSubmitEnable()
    }

    private fun fetchExchangeMachine() {
        mPresenter?.fetchExchangeMachine(0, 20)
    }

    override fun bindExchangeMachine(machineBean: ExchangeMachineBean?) {
        if (selectedMachine == null) {
            machineBean?.list?.let {
                if (it.isNotEmpty()) {
                    for (itemBean in it) {
                        if (itemBean != null) {
                            bindSelectedMachine(itemBean)
                            break
                        }
                    }
                }
            }
        }
    }

    private fun bindSelectedMachine(machineBean: ExchangeMachineItemBean?) {
        selectedMachine = machineBean
        tvTitle?.text = selectedMachine?.name ?: "--"
        tvPrice?.text = NumberUtils.numberScale(selectedMachine?.price)
        ivCover?.let {
            Glide.with(this).load(selectedMachine?.image).into(it)
        }
        setSubmitEnable()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RC_SELECT_MACHINE -> {
                    val machine: ExchangeMachineItemBean? =
                        data?.extras?.getParcelable("selectedMachine")
                    bindSelectedMachine(machine)
                }
                RC_SELECT_ADDRESS -> {
                    val address: AddressItemBean? = data?.extras?.getParcelable("address")
                    bindSelectedAddress(address)
                }
            }
        }
    }

    private fun showPasswordDialog() {
        PayPwdDialog(this@ExchangeActivity)
            .setNumRand(true)
            .setInputComplete(object : PayPwdDialog.OnInputCodeListener {
                override fun inputCodeComplete(dialog: Dialog?, password: String?) {
                    dialog?.dismiss()
                    submitExchange(password)
                }

                override fun inputCodeInput(dialog: Dialog?, password: String?) {

                }
            }).show()
    }

    private fun submitExchange(password: String?) {
        if (password.isNullOrBlank()) {
            showToast("请输入支付密码")
            return
        }
        if (selectedAddress == null) {
            showToast("请选择地址")
            return
        }
        if (selectedMachine == null) {
            showToast("请选择机具")
            return
        }
        val countStr = tvCount?.text?.toString() ?: "1"
        mPresenter?.submitExchangeMachine(
            countStr,
            selectedMachine?.type?.toString() ?: "",
            selectedAddress?.contact ?: "",
            selectedAddress?.mobile ?: "",
            (selectedAddress?.area ?: "") + (selectedAddress?.address ?: ""),
            password ?: ""
        )
    }

    override fun onSubmitExchangeSuccess() {
        showToast("兑换成功")
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddressEditSuccess(event: AddressEditSuccessEvent) {
        selectedAddress?.let {
            if (!event.address?.id.isNullOrBlank() && event.address?.id == it.id) {
                bindSelectedAddress(event.address)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddressDeleteSuccess(event: AddressDeleteSuccessEvent) {
        selectedAddress?.let {
            if (!event.addressID.isNullOrBlank() && event.addressID == it.id) {
                bindSelectedAddress(null)
            }
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}