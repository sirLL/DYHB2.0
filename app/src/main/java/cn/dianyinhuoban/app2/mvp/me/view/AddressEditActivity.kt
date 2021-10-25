package cn.dianyinhuoban.app2.mvp.me.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.AddressItemBean
import cn.dianyinhuoban.app2.event.AddressEditSuccessEvent
import cn.dianyinhuoban.app2.mvp.me.contract.AddressEditContract
import cn.dianyinhuoban.app2.mvp.me.presenter.AddressEditPresenter
import com.github.gzuliyujiang.wheelpicker.AddressPicker
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode
import com.github.gzuliyujiang.wheelpicker.entity.CityEntity
import com.github.gzuliyujiang.wheelpicker.entity.CountyEntity
import com.github.gzuliyujiang.wheelpicker.entity.ProvinceEntity
import com.github.gzuliyujiang.wheelpicker.utility.AddressJsonParser
import com.wareroom.lib_base.ui.BaseActivity
import com.wareroom.lib_base.utils.ValidatorUtils
import org.greenrobot.eventbus.EventBus

class AddressEditActivity : BaseActivity<AddressEditContract.Presenter?>(),
    AddressEditContract.View {

    companion object {
        fun openAddressEditActivity(
            activity: Activity,
            address: AddressItemBean,
            requestCode: Int
        ) {
            val intent = Intent(activity, AddressEditActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("address", address)
            intent.putExtras(bundle)
            activity.startActivityForResult(intent, requestCode)
        }

        fun openAddressEditActivity(
            fragment: Fragment,
            address: AddressItemBean,
            requestCode: Int
        ) {
            val intent = Intent(fragment.requireContext(), AddressEditActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("address", address)
            intent.putExtras(bundle)
            fragment.startActivityForResult(intent, requestCode)
        }

        fun openAddressEditActivity(context: Context) {
            val intent = Intent(context, AddressEditActivity::class.java)
            context.startActivity(intent)
        }
    }

    var address: AddressItemBean? = null
    var edName: EditText? = null
    var edPhone: EditText? = null
    var tvArea: TextView? = null
    var edAddress: EditText? = null
    var ivSwitch: ImageView? = null
    var btnSubmit: Button? = null

    override fun getPresenter(): AddressEditContract.Presenter? {
        return AddressEditPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_edit)
        setTitle(
            if (address == null) {
                "添加新地址"
            } else {
                "修改地址"
            }
        )
        initView()
        initEvent()
        bindData()
    }

    private fun initView() {
        edName = findViewById(R.id.ed_name)
        edPhone = findViewById(R.id.ed_phone)
        tvArea = findViewById(R.id.tv_area)
        edAddress = findViewById(R.id.ed_address)
        ivSwitch = findViewById(R.id.iv_switch)
        btnSubmit = findViewById(R.id.btn_submit)
    }

    override fun handleIntent(bundle: Bundle?) {
        super.handleIntent(bundle)
        address = bundle?.getParcelable("address")
    }

    private fun initEvent() {
        var textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                setSubmitEnable()
            }
        }
        edName?.addTextChangedListener(textWatcher)
        edPhone?.addTextChangedListener(textWatcher)
        tvArea?.addTextChangedListener(textWatcher)
        edAddress?.addTextChangedListener(textWatcher)
        tvArea?.setOnClickListener {
            showAddressPicker()
        }
        btnSubmit?.setOnClickListener {
            submitAddress()
        }
        ivSwitch?.setOnClickListener {
            it.isSelected = !it.isSelected
        }
    }

    private fun setSubmitEnable() {
        val name = edName?.text?.toString()
        val phone = edPhone?.text?.toString()
        val area = tvArea?.text?.toString()
        val address = edAddress?.text?.toString()
        btnSubmit?.isEnabled =
            !name.isNullOrBlank() && name.length >= 2
                    && !phone.isNullOrBlank() && ValidatorUtils.isMobile(phone)
                    && !area.isNullOrBlank() && area.length >= 2
                    && !address.isNullOrBlank() && address.length >= 2
    }

    private fun bindData() {
        address?.let {
            val name = it.contact
            val phone = it.mobile
            val area = it.area
            val addressDetail = it.address

            edName?.setText(name)
            edPhone?.setText(phone)
            tvArea?.text = area
            edAddress?.setText(addressDetail)
            name?.let { nameStr ->
                edName?.setSelection(nameStr.length)
            }
            phone?.let { phoneStr ->
                edPhone?.setSelection(phoneStr.length)
            }
            addressDetail?.let { addressStr ->
                edAddress?.setSelection(addressStr.length)
            }
            ivSwitch?.isSelected = "1" == it.isdefault
        }
    }

    private fun showAddressPicker() {
        val picker = AddressPicker(this)
        picker.setAddressMode(
            "city.json", AddressMode.PROVINCE_CITY_COUNTY,
            AddressJsonParser.Builder()
                .provinceCodeField("areaId")
                .provinceNameField("areaName")
                .provinceChildField("cities")
                .cityCodeField("areaId")
                .cityNameField("areaName")
                .cityChildField("counties")
                .countyCodeField("areaId")
                .countyNameField("areaName")
                .build()
        )
//        picker.setDefaultValue("贵州省", "毕节地区", "纳雍县")
        picker.setOnAddressPickedListener { province, city, county ->
            bindCheckedCity(province, city, county)
        }
        picker.titleView.text = "选择地区"
        picker.titleView.setTextColor(ContextCompat.getColor(this, R.color.color_454444))
        picker.titleView.textSize = 18f

        picker.cancelView.setTextColor(ContextCompat.getColor(this, R.color.color_bbbbbb))
        picker.cancelView.textSize = 14f

        picker.okView.setTextColor(ContextCompat.getColor(this, R.color.color_0187fd))
        picker.okView.textSize = 14f

        picker.contentView.setBackgroundColor(Color.TRANSPARENT)
        picker.bodyView.setBackgroundColor(Color.WHITE)
        picker.topLineView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.dy_color_divider
            )
        )
        picker.headerView.setBackgroundResource(R.drawable.shape_ffffff_radius_top_6)
        picker.show()
    }

    private fun bindCheckedCity(province: ProvinceEntity?, city: CityEntity?, area: CountyEntity?) {
        tvArea?.text = "${province?.name ?: ""}${city?.name ?: ""}${area?.name ?: ""}"
    }

    private fun submitAddress() {
        val name = edName?.text?.toString() ?: ""
        val phone = edPhone?.text?.toString() ?: ""
        val area = tvArea?.text?.toString() ?: ""
        val addressDetail = edAddress?.text?.toString() ?: ""
        mPresenter?.submitAddress(
            address?.id ?: "",
            name,
            phone,
            area,
            addressDetail,
            ivSwitch?.isSelected ?: false
        )
    }

    override fun onSubmitAddressSuccess(address: AddressItemBean) {
        EventBus.getDefault().post(AddressEditSuccessEvent(address))
        if (address.id.isNullOrBlank()) {
            showToast("添加地址成功")
        } else {
            showToast("修改地址成功")
        }
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }
}