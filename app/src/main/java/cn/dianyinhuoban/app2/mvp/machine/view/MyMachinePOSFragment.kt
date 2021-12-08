package cn.dianyinhuoban.app2.mvp.machine.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MachinePOSItemBean
import cn.dianyinhuoban.app2.bean.MyMachinePOSBean
import cn.dianyinhuoban.app2.mvp.machine.contract.MyPOSContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.MyPOSPresenter
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.BaseMultiListFragment
import com.wareroom.lib_base.ui.adapter.MultiAdapter
import com.wareroom.lib_base.ui.adapter.SimpleAdapter
import java.util.*

class MyMachinePOSFragment : BaseMultiListFragment<MachinePOSItemBean?, MyPOSContract.Presenter?>(),
    MyPOSContract.View {
    companion object {
        const val MACHINE_TYPE_ALL = "0"
        const val MACHINE_TYPE_NOT_ACTIVE = "1"
        const val MACHINE_TYPE_ACTIVATED = "2"
        private const val PAGE_SIZE = 20
        private const val ITEM_TYPE_NOT_ACTIVE = 0
        private const val ITEM_TYPE_ACTIVATED = 1

        fun newInstance(): MyMachinePOSFragment {
            val args = Bundle()
            val fragment = MyMachinePOSFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var allTab: LinearLayout? = null
    private var notActiveTab: LinearLayout? = null
    private var activatedTab: LinearLayout? = null
    private var tvAllNum: TextView? = null
    private var tvNotActiveNum: TextView? = null
    private var tvActivatedNum: TextView? = null

    private var machineType: String = MACHINE_TYPE_ALL

    override fun getPresenter(): MyPOSContract.Presenter? {
        return MyPOSPresenter(this)
    }

    override fun getContentView(): Int {
        return R.layout.fragment_my_machine_pos
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        allTab = contentView?.findViewById(R.id.tag_container_all)
        notActiveTab = contentView?.findViewById(R.id.tag_container_not_active)
        activatedTab = contentView?.findViewById(R.id.tag_container_activated)
        tvAllNum = contentView?.findViewById(R.id.tv_all_num)
        tvNotActiveNum = contentView?.findViewById(R.id.tv_not_active_num)
        tvActivatedNum = contentView?.findViewById(R.id.tv_activated_num)

        allTab?.setOnClickListener {
            if (!it.isSelected) {
                changeMachineType(MACHINE_TYPE_ALL)
            }
        }
        notActiveTab?.setOnClickListener {
            if (!it.isSelected) {
                changeMachineType(MACHINE_TYPE_NOT_ACTIVE)
            }
        }
        activatedTab?.setOnClickListener {
            if (!it.isSelected) {
                changeMachineType(MACHINE_TYPE_ACTIVATED)
            }
        }
        changeMachineType(MACHINE_TYPE_ALL)
    }

    private fun changeMachineType(machineType: String) {
        when (machineType) {
            MACHINE_TYPE_NOT_ACTIVE -> {
                notActiveTab?.isSelected = true
                activatedTab?.isSelected = false
                allTab?.isSelected = false
            }
            MACHINE_TYPE_ACTIVATED -> {
                activatedTab?.isSelected = true
                notActiveTab?.isSelected = false
                allTab?.isSelected = false
            }
            else -> {
                allTab?.isSelected = true
                activatedTab?.isSelected = false
                notActiveTab?.isSelected = false
            }
        }
        this.machineType = machineType
        autoRefresh()
    }


    override fun onRequest(page: Int) {
        mPresenter?.fetchMyPOS(page, PAGE_SIZE, "", machineType)
    }

    override fun getItemLayout(viewType: Int): Int {
        return if (viewType == ITEM_TYPE_ACTIVATED) {
            R.layout.item_my_machine_pos_activated
        } else {
            R.layout.item_my_machine_pos_not_active
        }
    }

    override fun bindMyPos(posBean: MyMachinePOSBean?) {
        loadData(posBean?.arylist)
        tvAllNum?.text = posBean?.allcount ?: "--"
        tvNotActiveNum?.text = posBean?.unactcount ?: "--"
        tvActivatedNum?.text = posBean?.actcount ?: "--"
    }

    override fun convert(
        viewHolder: MultiAdapter.SimpleViewHolder?,
        position: Int,
        viewType: Int,
        itemData: MachinePOSItemBean?
    ) {
        val icon = viewHolder?.getView<ImageView>(R.id.iv_status)
        viewHolder?.setText(R.id.tv_title, itemData?.name ?: "")
        viewHolder?.setText(R.id.tv_sn, itemData?.sn ?: "")

        if (viewType == ITEM_TYPE_ACTIVATED) {
            //已激活
            icon?.setImageResource(R.drawable.ic_tag_activated)
            //伙伴姓名
            viewHolder?.setText(R.id.tv_name, itemData?.client ?: "")
            //激活时间
            viewHolder?.setText(R.id.tv_date, itemData?.act_time ?: "")
        } else {
            //未激活
            icon?.setImageResource(R.drawable.ic_tag_not_active)
        }
    }

    override fun getItemViewType(position: Int, itemData: MachinePOSItemBean?): Int {
        return if (itemData != null) {
            if ("已激活" == itemData?.stateid) {
                ITEM_TYPE_ACTIVATED
            } else {
                ITEM_TYPE_NOT_ACTIVE
            }
        } else {
            ITEM_TYPE_NOT_ACTIVE
        }
    }

    override fun onItemClick(data: MachinePOSItemBean?, position: Int, viewType: Int) {

    }

}