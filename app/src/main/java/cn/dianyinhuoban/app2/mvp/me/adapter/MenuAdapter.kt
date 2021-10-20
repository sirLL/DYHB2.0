package cn.dianyinhuoban.app2.mvp.me.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MenuBean

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val data by lazy {
        mutableListOf<MenuBean>()
    }
    private var onMenuClickListener: OnMenuClickListener? = null

    fun setData(data: MutableList<MenuBean>?) {
        this.data.clear()
        data?.let {
            for (bean in it) {
                this.data.add(bean)
            }
        }
        notifyDataSetChanged()
    }

    fun setOnMenuClickListener(onMenuClickListener: OnMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_me_menu, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        var bean: MenuBean? = null
        if (data.size > position) {
            bean = data[position]
        }
        if (bean != null) {
            holder.imageView.setImageResource(bean.icon)
            holder.textView.text = bean.title
        } else {
            holder.imageView.setImageBitmap(null)
            holder.textView.text = ""
        }
        holder.itemView.setOnClickListener {
            bean?.let {
                onMenuClickListener?.let { listener ->
                    listener.onMenuClick(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView by lazy {
            itemView.findViewById(R.id.iv_icon)
        }
        val textView: TextView by lazy {
            itemView.findViewById(R.id.tv_title)
        }
    }

    interface OnMenuClickListener {
        fun onMenuClick(menuBean: MenuBean)
    }
}