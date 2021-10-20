package cn.dianyinhuoban.app2.mvp.home.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.HomeMenuBean
import cn.dianyinhuoban.app2.bean.HomeMenuItemBean
import com.bumptech.glide.Glide

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val data by lazy {
        mutableListOf<HomeMenuItemBean>()
    }
    private var onMenuClickListener: OnMenuClickListener? = null

    fun setData(data: MutableList<HomeMenuItemBean?>) {
        this.data.clear()
        if (data.isNotEmpty()) {
            for (bean in data) {
                bean?.let {
                    this.data.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun setOnMenuClickListener(onMenuClickListener: OnMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_menu, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        var bean: HomeMenuItemBean? = null
        if (data.size > position) {
            bean = data[position]
        }
        if (bean != null) {
            Glide.with(holder.imageView).load(bean.image).into(holder.imageView)
            holder.textView.text = bean.name
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
        fun onMenuClick(menuBean: HomeMenuItemBean)
    }
}