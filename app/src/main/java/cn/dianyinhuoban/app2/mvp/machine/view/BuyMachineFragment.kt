package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.GridSpacingItemDecoration
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MachineBean
import cn.dianyinhuoban.app2.bean.Product
import cn.dianyinhuoban.app2.bean.ProductX
import cn.dianyinhuoban.app2.bean.UserInfoBean
import cn.dianyinhuoban.app2.mvp.machine.adapter.ProductAdapter
import cn.dianyinhuoban.app2.mvp.machine.contract.BuyMachineContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.BuyMachinePresenter
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.wareroom.lib_base.ui.BaseFragment
import com.wareroom.lib_base.utils.DimensionUtils
import com.wareroom.lib_base.utils.NumberUtils
import com.wareroom.lib_base.utils.cache.MMKVUtil
import de.hdodenhof.circleimageview.CircleImageView

class BuyMachineFragment : BaseFragment<BuyMachineContract.Presenter?>(), BuyMachineContract.View {
    companion object {
        fun newInstance(type: String): BuyMachineFragment {
            val args = Bundle()
            args.putString("type", type)
            val fragment = BuyMachineFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var machineType: String? = ""
    private var selectedProductX: ProductX? = null

    private var tvName: TextView? = null
    private var tvPhone: TextView? = null
    private var ivAvatar: CircleImageView? = null
    private var ivCover: RoundedImageView? = null
    private var tvTitle: TextView? = null
    private var tvPrice: TextView? = null
    private var edCount: EditText? = null
    private var ivSubtract: ImageView? = null
    private var ivAdd: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private var tvAmount: TextView? = null
    private var btnSubmit: Button? = null

    override fun getPresenter(): BuyMachineContract.Presenter? {
        return BuyMachinePresenter(this)
    }

    override fun getContentView(): Int {
        return R.layout.fragment_buy_machine
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        machineType = arguments?.getString("type", "")

        tvName = contentView?.findViewById(R.id.tv_name)
        tvPhone = contentView?.findViewById(R.id.tv_phone)
        ivAvatar = contentView?.findViewById(R.id.iv_avatar)
        ivCover = contentView?.findViewById(R.id.iv_cover)
        tvTitle = contentView?.findViewById(R.id.tv_title)
        tvPrice = contentView?.findViewById(R.id.tv_price)
        edCount = contentView?.findViewById(R.id.ed_count)
        ivSubtract = contentView?.findViewById(R.id.iv_subtract)
        ivAdd = contentView?.findViewById(R.id.iv_add)
        recyclerView = contentView?.findViewById(R.id.recycler_view)
        tvAmount = contentView?.findViewById(R.id.tv_amount)
        btnSubmit = contentView?.findViewById(R.id.btn_submit)
        setupEvent()

        fetchMerchant()
        fetchMachine()
    }

    private fun setupEvent() {
        //点击加
        ivAdd?.setOnClickListener {
            val countStr = edCount?.text?.toString() ?: "0"
            val count = if (countStr.isEmpty()) {
                0
            } else {
                countStr.toInt()
            }
            edCount?.setText((count + 1).toString())
        }

        //点击减
        ivSubtract?.setOnClickListener {
            val countStr = edCount?.text?.toString() ?: "0"
            val count = if (countStr.isEmpty()) {
                0
            } else {
                countStr.toInt()
            }
            edCount?.setText(
                if (count <= 0) {
                    "0"
                } else {
                    (count - 1).toString()
                }
            )
        }

        //数量输入监听
        edCount?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                changeAmount()
            }
        })
    }

    /**
     * 计算合计
     */
    private fun changeAmount() {
        val countStr = edCount?.text?.toString() ?: "0"
        val priceStr = selectedProductX?.unit_price ?: "0"
        val count = if (countStr.isEmpty()) {
            0
        } else {
            countStr.toInt()
        }
        val price = if (priceStr.isEmpty()) {
            0.0
        } else {
            priceStr.toDouble()
        }
        tvAmount?.text = NumberUtils.numberScale(count * price)
        setSubmitEnable()
    }

    private fun setSubmitEnable() {
        val countStr = edCount?.text?.toString() ?: "0"
        val priceStr = selectedProductX?.unit_price ?: "0"
        val count = if (countStr.isEmpty()) {
            0
        } else {
            countStr.toInt()
        }
        val price = if (priceStr.isEmpty()) {
            0.0
        } else {
            priceStr.toDouble()
        }
        btnSubmit?.isEnabled = selectedProductX != null && count > 0 && price > 0
    }

    /**
     * 获取用户信息
     */
    private fun fetchMerchant() {
        mPresenter?.fetchMerchant()
    }

    /**
     * 绑定用户信息
     */
    override fun bindMerchant(merchantBean: UserInfoBean?) {
        tvName?.text = merchantBean?.name ?: "--"
        tvPhone?.text = MMKVUtil.getPhone()
        ivAvatar?.let {
            Glide.with(this).load(merchantBean?.image ?: "").error(R.drawable.img_def_avatar)
                .placeholder(R.drawable.img_def_avatar).into(it)
        }
    }

    /**
     * 获取设备信息
     */
    private fun fetchMachine() {
        mPresenter?.fetchMachine(machineType ?: "")
    }

    /**
     * 绑定设备信息
     */
    override fun bindMachine(machineBean: MachineBean?) {
        var product: Product? = null
        machineBean?.let {
            it.products?.let { productList ->
                if (productList.isNotEmpty()) {
                    product = productList[0]
                }
            }
        }
        tvTitle?.text = product?.product_name ?: "--"
        val adapter = ProductAdapter()

        //选中套餐回调
        adapter.setOnItemSelectedCallback(object : ProductAdapter.OnItemSelectedCallback {
            override fun onItemSelected(position: Int, productX: ProductX) {
                tvPrice?.text = "${NumberUtils.numberScale(productX.unit_price)}/台"
                ivCover?.let {
                    Glide.with(this@BuyMachineFragment).load(productX.icon).into(it)
                }
                selectedProductX = productX
                changeAmount()
            }
        })

        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(
            GridSpacingItemDecoration(
                3,
                DimensionUtils.dp2px(requireContext(), 10),
                false
            )
        )
        adapter.setData(product?.product_list)
    }
}