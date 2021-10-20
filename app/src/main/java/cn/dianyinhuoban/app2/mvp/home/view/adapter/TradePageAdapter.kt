package cn.dianyinhuoban.app2.mvp.home.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.UserInfoBean
import com.wareroom.lib_base.utils.NumberUtils

class TradePageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ITEM_TYPE_TEAM = 0
        private const val ITEM_TYPE_YESTERDAY = 1
    }

    private var data: UserInfoBean? = null
    fun setData(data: UserInfoBean?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = if (viewType == ITEM_TYPE_YESTERDAY) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_trade_yesterday, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_trade_team, parent, false)
        }
        return if (viewType == ITEM_TYPE_YESTERDAY) {
            YesterdayViewHolder(itemView)
        } else {
            TeamViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TeamViewHolder) {
            holder.tvActivationNum?.text = data?.posact ?: "--"
            holder.tvCodeNum?.text = data?.codeact ?: "--"
            holder.tvActivationTotal?.text = data?.teamact ?: "--"
            holder.tvMachineTradeNum?.text = NumberUtils.numberScale(data?.teamPosVolume)
            holder.tvCodeTradeNum?.text = NumberUtils.numberScale(data?.teamCodeVolume)
            holder.tvTradeTotal?.text = NumberUtils.numberScale(data?.teamvolume)
        } else if (holder is YesterdayViewHolder) {
            holder.tvMachineTradePersonal?.text = NumberUtils.numberScale(data?.yesterdayPosVolume)
            holder.tvCodeTradePersonal?.text = NumberUtils.numberScale(data?.yesterdayCodeVolume)
            holder.tvTradeTotalPersonal?.text = NumberUtils.numberScale(data?.yesterdayvolume)
            holder.tvMachineTradeTeam?.text = NumberUtils.numberScale(data?.yesterdayPosTeamVolume)
            holder.tvCodeTradeTeam?.text = NumberUtils.numberScale(data?.yesterdayCodeTeamVolume)
            holder.tvTradeTotalTeam?.text = NumberUtils.numberScale(data?.yesterdayteamvolume)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 1) ITEM_TYPE_YESTERDAY else ITEM_TYPE_TEAM
    }

    override fun getItemCount(): Int {
        return 2
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvActivationNum: TextView? = null
        var tvCodeNum: TextView? = null
        var tvActivationTotal: TextView? = null
        var tvMachineTradeNum: TextView? = null
        var tvCodeTradeNum: TextView? = null
        var tvTradeTotal: TextView? = null

        init {
            tvActivationNum = itemView.findViewById(R.id.tv_activation_num)
            tvCodeNum = itemView.findViewById(R.id.tv_code_num)
            tvActivationTotal = itemView.findViewById(R.id.tv_activation_total)
            tvMachineTradeNum = itemView.findViewById(R.id.tv_machine_trade_num)
            tvCodeTradeNum = itemView.findViewById(R.id.tv_code_trade_num)
            tvTradeTotal = itemView.findViewById(R.id.tv_trade_total)
        }
    }

    class YesterdayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMachineTradePersonal: TextView? = null
        var tvCodeTradePersonal: TextView? = null
        var tvTradeTotalPersonal: TextView? = null
        var tvMachineTradeTeam: TextView? = null
        var tvCodeTradeTeam: TextView? = null
        var tvTradeTotalTeam: TextView? = null

        init {
            tvMachineTradePersonal = itemView.findViewById(R.id.tv_machine_trade_personal)
            tvCodeTradePersonal = itemView.findViewById(R.id.tv_code_trade_personal)
            tvTradeTotalPersonal = itemView.findViewById(R.id.tv_trade_total_personal)
            tvMachineTradeTeam = itemView.findViewById(R.id.tv_machine_trade_team)
            tvCodeTradeTeam = itemView.findViewById(R.id.tv_code_trade_team)
            tvTradeTotalTeam = itemView.findViewById(R.id.tv_trade_total_team)
        }
    }
}