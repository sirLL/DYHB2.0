package cn.dianyinhuoban.app2.mvp.machine.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MyMachineDYFRateBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyDYFRateContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.MyDYFRatePresenter
import com.gyf.immersionbar.ImmersionBar
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class MyMachineDYFRateActivity : BaseActivity<MyDYFRateContract.Presenter?>(),
    MyDYFRateContract.View {
    companion object {
        fun openMyMachineDYFRateActivity(context: Context, rateID: String) {
            val intent = Intent(context, MyMachineDYFRateActivity::class.java)
            val bundle = Bundle()
            bundle.putString("rateID", rateID)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    private var tvLimit: TextView? = null
    private var tvFlashCredit: TextView? = null
    private var tvFlashDebit: TextView? = null
    private var tvScanCredit: TextView? = null
    private var tvScanDebit: TextView? = null
    private var tvScanCloud: TextView? = null
    private var tvFlashTips: TextView? = null
    private var tvScanTips: TextView? = null

    private var mRateID: String? = null
    override fun getRootView(): Int {
        return R.layout.activity_my_machine_dyf_rate_root
    }

    override fun getBackButtonIcon(): Int {
        return R.drawable.dy_base_ic_back_white
    }

    override fun getToolbarColor(): Int {
        return Color.TRANSPARENT
    }

    override fun initStatusBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .autoDarkModeEnable(isDarkModeEnable)
            .autoStatusBarDarkModeEnable(isDarkModeEnable)
            .statusBarView(findViewById(R.id.status_bar))
            .statusBarDarkFont(true)
            .flymeOSStatusBarFontColor(R.color.black)
            .init()
    }

    override fun handleIntent(bundle: Bundle?) {
        super.handleIntent(bundle)
        mRateID = bundle?.getString("rateID", "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_machine_dyf_rate)
        initView()
        mRateID?.let {
            mPresenter?.fetchMyDYFRate(it)
        }
    }

    private fun initView() {
        tvLimit = findViewById(R.id.tv_limit)
        tvFlashCredit = findViewById(R.id.tv_flash_credit)
        tvFlashDebit = findViewById(R.id.tv_flash_debit)
        tvScanCredit = findViewById(R.id.tv_scan_credit)
        tvScanDebit = findViewById(R.id.tv_scan_debit)
        tvScanCloud = findViewById(R.id.tv_scan_cloud)
        tvFlashTips = findViewById(R.id.tv_tips_flash)
        tvScanTips = findViewById(R.id.tv_tips_scan)
    }

    override fun getPresenter(): MyDYFRateContract.Presenter? {
        return MyDYFRatePresenter(this)
    }

    override fun bindMyDYFRate(bean: MyMachineDYFRateBean?) {
        tvLimit?.text = bean?.rate?.total_limit ?: "--"
        tvFlashCredit?.text = "${bean?.rate?.credit ?: "--"}%"
        tvFlashDebit?.text = "${bean?.rate?.debit ?: "--"}%"
        tvScanCredit?.text = "${bean?.rate?.large_credit ?: "--"}%"
        tvScanDebit?.text = "${bean?.rate?.large_debit ?: "--"}%"
        tvScanCloud?.text = "--%"

        tvFlashTips?.text = bean?.rate?.sing_trade_max
        tvScanTips?.text = "微信/支付宝/云闪付 (＜1000) ${bean?.rate?.qrcode}%"
    }
}