package cn.dianyinhuoban.app2.mvp.home.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.UserInfoBean

class MachinePageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ITEM_TYPE_CURRENT = 0
        private const val ITEM_TYPE_LAST = 1
    }

    private var data: UserInfoBean? = null
    fun setData(data: UserInfoBean?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = if (viewType == ITEM_TYPE_LAST) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_last_month, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_current_month, parent, false)
        }
        return if (viewType == ITEM_TYPE_LAST) {
            LastViewHolder(itemView)
        } else {
            CurrentViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CurrentViewHolder) {
            holder.tvMachineNum?.text = data?.monthPosCount ?: "--"
            holder.tvActivationMachine?.text = data?.monthPosActCount ?: ""
            holder.tvCodeNum?.text = data?.monthCodeCount ?: ""
            holder.tvActivationCode?.text = data?.monthCodeActCount ?: ""
        } else if (holder is LastViewHolder) {
            holder.tvMachineNum?.text = data?.lastMonthPosCount ?: "--"
            holder.tvActivationMachine?.text = data?.lastMonthPosActCount ?: ""
            holder.tvCodeNum?.text = data?.lastMonthCodeCount ?: ""
            holder.tvActivationCode?.text = data?.lastMonthCodeActCount ?: ""
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 1) ITEM_TYPE_LAST else ITEM_TYPE_CURRENT
    }

    override fun getItemCount(): Int {
        return 2
    }

    class CurrentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMachineNum: TextView? = null
        var tvActivationMachine: TextView? = null
        var tvCodeNum: TextView? = null
        var tvActivationCode: TextView? = null

        init {
            tvMachineNum = itemView.findViewById(R.id.tv_machine_num)
            tvActivationMachine = itemView.findViewById(R.id.tv_activation_machine)
            tvCodeNum = itemView.findViewById(R.id.tv_code_num)
            tvActivationCode = itemView.findViewById(R.id.tv_activation_code)
        }
    }

    class LastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMachineNum: TextView? = null
        var tvActivationMachine: TextView? = null
        var tvCodeNum: TextView? = null
        var tvActivationCode: TextView? = null

        init {
            tvMachineNum = itemView.findViewById(R.id.tv_machine_num)
            tvActivationMachine = itemView.findViewById(R.id.tv_activation_machine)
            tvCodeNum = itemView.findViewById(R.id.tv_code_num)
            tvActivationCode = itemView.findViewById(R.id.tv_activation_code)
        }
    }
}