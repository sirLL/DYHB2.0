package cn.dianyinhuoban.app2.mvp.wallet.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.IncomeBean
import cn.dianyinhuoban.app2.bean.IntegralBean
import cn.dianyinhuoban.app2.mvp.wallet.contract.IncomeContract
import cn.dianyinhuoban.app2.mvp.wallet.presenter.IncomePresenter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wareroom.lib_base.ui.BaseFragment
import com.wareroom.lib_base.utils.NumberUtils

class IncomeFragment : BaseFragment<IncomeContract.Presenter?>(), IncomeContract.View {
    companion object {
        fun newInstance(): IncomeFragment {
            val args = Bundle()
            val fragment = IncomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var refreshLayout: SmartRefreshLayout? = null
    private var tvTotal: TextView? = null
    private var tvIncomeToday: TextView? = null
    private var tvIncomePersonal: TextView? = null
    private var tvIncomeManager: TextView? = null
    private var tvIncomeCashBack: TextView? = null
    private var tvIntegralDq: TextView? = null
    private var tvIntegralCp: TextView? = null

    override fun getPresenter(): IncomeContract.Presenter? {
        return IncomePresenter(this)
    }

    override fun getContentView(): Int {
        return R.layout.fragment_income
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        refreshLayout = contentView?.findViewById(R.id.refresh_layout)
        tvTotal = contentView?.findViewById(R.id.tv_total_balance)
        tvIncomeToday = contentView?.findViewById(R.id.tv_income_today)
        tvIncomePersonal = contentView?.findViewById(R.id.tv_income_personal)
        tvIncomeManager = contentView?.findViewById(R.id.tv_income_manager)
        tvIncomeCashBack = contentView?.findViewById(R.id.tv_income_cash_back)
        tvIntegralCp = contentView?.findViewById(R.id.tv_integral_cp)
        tvIntegralDq = contentView?.findViewById(R.id.tv_integral_dq)

        contentView?.findViewById<TextView>(R.id.tv_withdraw)?.setOnClickListener {
            //TODO
        }
        setupRefreshLayout()

        loadData()
    }

    private fun setupRefreshLayout() {
        refreshLayout?.setRefreshHeader(ClassicsHeader(requireContext()))
        refreshLayout?.setEnableLoadMore(false)
        refreshLayout?.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        fetchIncome()
        fetchIntegral()
    }


    /******************************************收益信息 START********************************************/
    private fun fetchIncome() {
        mPresenter?.fetchIncome()
    }

    override fun bindIncome(incomeBean: IncomeBean?) {
        refreshLayout?.finishRefresh()
        tvTotal?.text = NumberUtils.numberScale(incomeBean?.money)
        tvIncomeToday?.text = NumberUtils.numberScale(incomeBean?.dayincome)
        tvIncomePersonal?.text = NumberUtils.numberScale(incomeBean?.personal)
        tvIncomeManager?.text = NumberUtils.numberScale(incomeBean?.team)
        tvIncomeCashBack?.text = NumberUtils.numberScale(incomeBean?.cashback)
    }
    /******************************************收益信息 END********************************************/


    /******************************************收益信息 START********************************************/
    private fun fetchIntegral() {
        mPresenter?.fetchIntegral()
    }

    override fun bindIntegral(integralBean: IntegralBean?) {
        refreshLayout?.finishRefresh()
        tvIntegralDq?.text = NumberUtils.numberScale(integralBean?.integral?.activity_integral_dq)
        tvIntegralCp?.text = NumberUtils.numberScale(integralBean?.integral?.activity_integral_cp)
    }
    /******************************************收益信息 END********************************************/

}