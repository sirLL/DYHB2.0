package cn.dianyinhuoban.app2.mvp.me.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MenuBean
import cn.dianyinhuoban.app2.mvp.me.adapter.ActionAdapter
import cn.dianyinhuoban.app2.mvp.me.adapter.MenuAdapter
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseFragment
import de.hdodenhof.circleimageview.CircleImageView

class MeFragment : BaseFragment<IPresenter?>() {
    companion object {
        fun newInstance(): MeFragment {
            val args = Bundle()
            val fragment = MeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var ivAvatar: CircleImageView? = null
    private var tvNick: TextView? = null
    private var ivJob: ImageView? = null
    private var tvLevel: TextView? = null
    private var progressBar: ProgressBar? = null
    private var tvProgressBar: TextView? = null
    private var rvMenu: RecyclerView? = null
    private var rvAction: RecyclerView? = null
    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter()
    }
    private val actionAdapter: ActionAdapter by lazy {
        ActionAdapter()
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun getContentView(): Int {
        return R.layout.fragment_me
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        ivAvatar = contentView?.findViewById(R.id.iv_avatar)
        tvNick = contentView?.findViewById(R.id.tv_nick)
        ivJob = contentView?.findViewById(R.id.iv_job)
        tvLevel = contentView?.findViewById(R.id.tv_level)
        progressBar = contentView?.findViewById(R.id.progress_bar)
        tvProgressBar = contentView?.findViewById(R.id.tv_progress)
        rvMenu = contentView?.findViewById(R.id.rv_menu)
        rvAction = contentView?.findViewById(R.id.rv_action)

        setupMenu()
        setupAction()
    }

    private fun setupMenu() {
        val menuData = mutableListOf<MenuBean>()
        menuData.add(MenuBean(1, R.drawable.ic_me_menu_machine, "我的机具"))
        menuData.add(MenuBean(2, R.drawable.ic_me_menu_transfer_record, "划拨记录"))
        menuData.add(MenuBean(3, R.drawable.ic_me_menu_verified, "实名认证"))
        menuData.add(MenuBean(4, R.drawable.ic_me_menu_order_record, "订单记录"))
        menuData.add(MenuBean(5, R.drawable.ic_me_menu_bank_card, "绑定银行卡"))
        menuData.add(MenuBean(6, R.drawable.ic_me_menu_merchant_apply, "商户申请"))
        rvMenu?.adapter = menuAdapter
        menuAdapter.setData(menuData)
    }

    private fun setupAction() {
        val menuData = mutableListOf<MenuBean>()
        menuData.add(MenuBean(1, R.drawable.ic_me_action_add_bank_card, "添加支付银行卡"))
        menuData.add(MenuBean(2, R.drawable.ic_me_action_update_merchant, "商户信息修改"))
        menuData.add(MenuBean(3, R.drawable.ic_me_action_merchant_certificate, "商户证件上传"))
        menuData.add(MenuBean(4, R.drawable.ic_me_action_online_service, "在线客服"))
        rvAction?.adapter = actionAdapter
        actionAdapter.setData(menuData)
    }
}