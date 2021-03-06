package cn.dianyinhuoban.app2.mvp.home.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.*
import cn.dianyinhuoban.app2.mvp.home.contract.HomeContract
import cn.dianyinhuoban.app2.mvp.home.presenter.HomePresenter
import cn.dianyinhuoban.app2.mvp.home.view.adapter.BannerViewAdapter
import cn.dianyinhuoban.app2.mvp.home.view.adapter.MachinePageAdapter
import cn.dianyinhuoban.app2.mvp.home.view.adapter.MenuAdapter
import cn.dianyinhuoban.app2.mvp.home.view.adapter.TradePageAdapter
import cn.dianyinhuoban.app2.mvp.machine.view.BuyMachineActivity
import cn.dianyinhuoban.app2.mvp.machine.view.MyMachineActivity
import cn.dianyinhuoban.app2.widget.dialog.SetRankDialog
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wareroom.lib_base.ui.BaseFragment
import com.wareroom.lib_base.utils.DimensionUtils
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator


class HomeFragment : BaseFragment<HomeContract.Presenter?>(), HomeContract.View {
    private var topBannerView: Banner<BannerItemBean, BannerViewAdapter>? =
        null
    private var centerBannerView: Banner<BannerItemBean, BannerViewAdapter>? =
        null
    private val topBannerAdapter by lazy {
        BannerViewAdapter(mutableListOf(), 20)
    }
    private val centerBannerAdapter by lazy {
        BannerViewAdapter(mutableListOf(), 20)
    }
    private var refreshLayout: SmartRefreshLayout? = null
    private var menuRecyclerView: RecyclerView? = null
    private var tradeViewPager: ViewPager2? = null
    private var machineViewPager: ViewPager2? = null
    private var tvTeamTab: TextView? = null
    private var tvYesterdayTab: TextView? = null
    private var tvCurrentMonthTab: TextView? = null
    private var tvLastMonthTab: TextView? = null
    private val tradePageAdapter by lazy {
        TradePageAdapter()
    }
    private val machinePageAdapter by lazy {
        MachinePageAdapter()
    }
    private var setRankDialog: SetRankDialog? = null

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getPresenter(): HomeContract.Presenter? {
        return HomePresenter(this)
    }

    override fun getContentView(): Int {
        return R.layout.fragment_home
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        refreshLayout = contentView?.findViewById(R.id.refresh_layout)
        topBannerView = contentView?.findViewById(R.id.top_banner)
        centerBannerView = contentView?.findViewById(R.id.center_banner)
        menuRecyclerView = contentView?.findViewById(R.id.recycler_view_menu)
        tradeViewPager = contentView?.findViewById(R.id.vp_trade)
        machineViewPager = contentView?.findViewById(R.id.vp_machine)
        tvTeamTab = contentView?.findViewById(R.id.tv_trade_team)
        tvYesterdayTab = contentView?.findViewById(R.id.tv_trade_yesterday)
        tvCurrentMonthTab = contentView?.findViewById(R.id.tv_current_month)
        tvLastMonthTab = contentView?.findViewById(R.id.tv_last_month)

        setupRefreshLayout()

        //????????????Banner
        setupTopBannerView()
        //????????????ViewPager
        setupTradeViewPager()
        //????????????ViewPager
        setupMachineViewPager()
        //????????????Banner
        setupCenterBannerView()
        loadData()
    }

    private fun setupRefreshLayout() {
        refreshLayout?.setRefreshHeader(ClassicsHeader(requireContext()))
        refreshLayout?.setEnableLoadMore(false)
        refreshLayout?.setOnRefreshListener {
            loadData()
        }
    }

    /**
     * ????????????
     */
    private fun loadData() {
        mPresenter?.fetchUserInfo()
        fetchTopBanner()
        fetchMenu()
    }

    override fun bindUserInfoBean(userInfoBean: UserInfoBean?) {
        refreshLayout?.let {
            Log.d(TAG, "bindUserInfoBean: ${it.isRefreshing}")
            if (it.isRefreshing) {
                it.finishRefresh()
            }
        }
        bindCenterBanner(
            userInfoBean?.activitylist,
            userInfoBean?.activity_w,
            userInfoBean?.activity_h
        )
        bindTraderData(userInfoBean)
        bindMachineData(userInfoBean)

        userInfoBean?.let {
            if (it.is_setsubrank.isNullOrEmpty() || "0" == it.is_setsubrank) {
                showSetRankDialog(it.upper_tel)
            }
        }
    }


    /******************************************?????? START********************************************/
    private fun fetchMenu() {
        mPresenter?.fetchMenu()
    }

    override fun bindMenu(data: List<HomeMenuItemBean?>?) {
        val adapter = MenuAdapter()
        val menuList = mutableListOf<HomeMenuItemBean?>()
        data?.let {
            for (bean in it) {
                bean?.let { menu ->
                    menuList.add(menu)
                }
            }
        }
        adapter.setData(menuList)
        menuRecyclerView?.adapter = adapter
        adapter.setOnMenuClickListener(object : MenuAdapter.OnMenuClickListener {
            override fun onMenuClick(menuBean: HomeMenuItemBean) {
                this@HomeFragment.onMenuClick(menuBean)
            }
        })
    }

    /**
     * ????????????
     */
    private fun onMenuClick(menuBean: HomeMenuItemBean) {
        if ("1" == menuBean.type) {
            //??????????????????
            when (menuBean.tag) {
                //0???????????????1???????????????2??????app???3???????????????4???????????????5???????????????6???????????????7????????????
                "0" -> {
                    //????????????
                    startActivity(Intent(requireContext(), MyMachineActivity::class.java))
                }
                "1" -> {
                    //1????????????
                }
                "2" -> {
                    //2??????app
                    startActivity(Intent(requireContext(), ShopCodeActivity::class.java))
                }
                "3" -> {
                    //3????????????
                    startActivity(Intent(requireContext(), BuyMachineActivity::class.java))
                }
                "4" -> {
                    //4????????????
                }
                "5" -> {
                    //5????????????
                }
                "6" -> {
                    //6????????????
                }
                "7" -> {
                    //7????????????
                }
            }
        } else if ("2" == menuBean.type) {
            //????????????
        }
    }
    /******************************************?????? END********************************************/


    /******************************************????????????Banner START********************************************/
    /**
     * ????????????Banner
     */
    private fun setupCenterBannerView() {
        centerBannerAdapter.setOnBannerListener { data, position ->
            {

            }
        }
        centerBannerView?.addBannerLifecycleObserver(this)
            ?.setAdapter(centerBannerAdapter)
            ?.indicator = CircleIndicator(requireContext())
    }

    /**
     * ??????????????????Banner??????
     */
    private fun bindCenterBanner(data: List<ActivityBean>?, imgWidth: String?, imgHeight: String?) {
        if (data.isNullOrEmpty()) {
            centerBannerView?.visibility = View.GONE
        } else {
            centerBannerView?.visibility = View.VISIBLE
            val screenWidth = DimensionUtils.getScreenWidth(requireContext())
            val dp2px32 = DimensionUtils.dp2px(requireContext(), 32)
            val bannerWidth = screenWidth - dp2px32
            val imgWidthDou = if (TextUtils.isEmpty(imgWidth)) {
                0.0
            } else {
                imgWidth?.toDouble() ?: 0.0
            }
            val imgHeightDou = if (TextUtils.isEmpty(imgHeight)) {
                0.0
            } else {
                imgHeight?.toDouble() ?: 0.0
            }
            val rate = if (imgWidthDou == 0.0) {
                2.0
            } else {
                imgHeightDou / imgWidthDou
            }
            val bannerHeight = bannerWidth.times(rate)
            val lp = centerBannerView?.layoutParams as ConstraintLayout.LayoutParams
            lp.dimensionRatio = "h,$imgWidthDou:$imgHeightDou"
            centerBannerView?.layoutParams = lp

            val bannerData = mutableListOf<BannerItemBean>()
            data?.let {
                if (it.isNotEmpty()) {
                    for (activityBean in it) {
                        activityBean?.let { bean ->
                            bannerData.add(BannerItemBean(bean.img_url, 0, bean.detail_url))
                        }
                    }
                }
            }
            centerBannerAdapter.setDatas(bannerData)
        }
    }
    /******************************************????????????Banner End********************************************/


    /******************************************??????Banner START********************************************/
    /**
     * ????????????Banner
     */
    private fun setupTopBannerView() {
        topBannerAdapter.setOnBannerListener { data, position ->
            {

            }
        }
        topBannerView?.addBannerLifecycleObserver(this)
            ?.setAdapter(topBannerAdapter)
            ?.indicator = CircleIndicator(requireContext())

    }

    /**
     * ????????????Banner??????
     */
    private fun fetchTopBanner() {
        mPresenter?.fetchTopBanner()
    }

    /**
     * ????????????Banner??????
     */
    override fun bindTopBanner(bannerBean: BannerBean?) {
        val bannerData = mutableListOf<BannerItemBean>()
        bannerBean?.let {
            it?.arylist?.let { itemBeanList ->
                if (itemBeanList.isNotEmpty()) {
                    for (itemBean in itemBeanList) {
                        bannerData.add(itemBean)
                    }
                }
            }
        }
        topBannerAdapter?.setDatas(bannerData)
    }
    /******************************************??????Banner End********************************************/

    /******************************************????????????  Start********************************************/
    /**
     * ????????????ViewPager
     */
    private fun setupTradeViewPager() {
        tradeViewPager?.adapter = tradePageAdapter
        tradeViewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    onTeamTabSelected()
                } else {
                    onYesterdayTabSelected()
                }
            }
        })
        tvTeamTab?.setOnClickListener {
            tradeViewPager?.currentItem = 0
        }
        tvYesterdayTab?.setOnClickListener {
            tradeViewPager?.currentItem = 1
        }
    }


    private fun onTeamTabSelected() {
        tvTeamTab?.isSelected = true
        tvYesterdayTab?.isSelected = false
    }

    private fun onYesterdayTabSelected() {
        tvTeamTab?.isSelected = false
        tvYesterdayTab?.isSelected = true
    }

    private fun bindTraderData(data: UserInfoBean?) {
        tradePageAdapter.setData(data)
    }
    /******************************************????????????ViewPager  END********************************************/


    /******************************************????????????  Start********************************************/
    /**
     * ????????????ViewPager
     */
    private fun setupMachineViewPager() {
        machineViewPager?.adapter = machinePageAdapter
        machineViewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    onCurrentMonthTabSelected()
                } else {
                    onLastMonthTabSelected()
                }
            }
        })
        tvCurrentMonthTab?.setOnClickListener {
            machineViewPager?.currentItem = 0
        }
        tvLastMonthTab?.setOnClickListener {
            machineViewPager?.currentItem = 1
        }
    }

    private fun onCurrentMonthTabSelected() {
        tvCurrentMonthTab?.isSelected = true
        tvLastMonthTab?.isSelected = false
    }

    private fun onLastMonthTabSelected() {
        tvCurrentMonthTab?.isSelected = false
        tvLastMonthTab?.isSelected = true
    }

    private fun bindMachineData(data: UserInfoBean?) {
        machinePageAdapter.setData(data)
    }

    /******************************************????????????  END********************************************/


    /**
     * ???????????????????????????????????????????????????
     */
    private fun showSetRankDialog(phone: String?) {
        if (setRankDialog == null) {
            setRankDialog = SetRankDialog.newInstance(phone)
        }
        setRankDialog?.let {
            it.show(childFragmentManager, "SetRankDialog")
//            it.dialog?.let { dialog ->
//                if (dialog.isShowing) {
//                    it.show(childFragmentManager, "SetRankDialog")
//                }
//            }
        }

    }

    override fun onStart() {
        super.onStart()
        topBannerView?.start()
    }

    override fun onStop() {
        super.onStop()
        topBannerView?.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        topBannerView?.destroy()
    }

}