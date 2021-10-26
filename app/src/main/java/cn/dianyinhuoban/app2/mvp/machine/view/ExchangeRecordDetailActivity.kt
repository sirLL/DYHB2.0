package cn.dianyinhuoban.app2.mvp.machine.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.ExchangeRecordItemBean
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class ExchangeRecordDetailActivity : BaseActivity<IPresenter?>() {

    companion object {
        fun openExchangeRecordDetail(context: Context, recordBean: ExchangeRecordItemBean) {
            val intent = Intent(context, ExchangeRecordDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("recordBean", recordBean)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    var itemBean: ExchangeRecordItemBean? = null
    var ivCover: RoundedImageView? = null
    var tvTitle: TextView? = null
    var tvCount: TextView? = null
    var tvDate: TextView? = null
    var tvNote: TextView? = null
    var tvName: TextView? = null
    var tvPhone: TextView? = null
    var tvAddress: TextView? = null

    override fun handleIntent(bundle: Bundle?) {
        super.handleIntent(bundle)
        itemBean = bundle?.getParcelable("recordBean")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_record_detail)
        setTitle("详情")
        initView()
        bindData()
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    private fun initView() {
        ivCover = findViewById(R.id.iv_cover)
        tvTitle = findViewById(R.id.tv_title)
        tvCount = findViewById(R.id.tv_count)
        tvDate = findViewById(R.id.tv_date)
        tvNote = findViewById(R.id.tv_note)
        tvName = findViewById(R.id.tv_name)
        tvPhone = findViewById(R.id.tv_phone)
        tvAddress = findViewById(R.id.tv_address)
    }

    private fun bindData() {
        tvName?.text = itemBean?.contact ?: ""
        tvPhone?.text = itemBean?.telephone ?: ""
        tvAddress?.text = itemBean?.address ?: ""
        tvTitle?.text = itemBean?.goods_name ?: ""
        tvCount?.text = itemBean?.exchange ?: ""
        tvDate?.text = itemBean?.add_time ?: ""
        tvNote?.text = itemBean?.note ?: ""
        ivCover?.let {
            Glide.with(this).load(itemBean?.goods_image ?: "").into(it)
        }
    }
}