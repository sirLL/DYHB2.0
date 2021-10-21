package cn.dianyinhuoban.app2.mvp.machine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.ProductX

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var onItemSelectedCallback: OnItemSelectedCallback? = null
    private var selectedProductX: ProductX? = null
    private val data by lazy {
        mutableListOf<ProductX>()
    }

    fun setData(data: List<ProductX?>?) {
        this.data.clear()
        data?.let {
            for (product_x in data) {
                product_x?.let {
                    this.data.add(it)
                    if (selectedProductX == null) {
                        selectedProductX = it
                        onSelectedProductX(data.indexOf(it), it)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    fun setOnItemSelectedCallback(onItemSelectedCallback: OnItemSelectedCallback) {
        this.onItemSelectedCallback = onItemSelectedCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_buy_machine_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        var itemBean: ProductX? = null
        if (data.size > position) {
            itemBean = data[position]
        }
        holder.tvTitle?.text = itemBean?.meal_name
        holder.itemView.isSelected =
            selectedProductX != null && itemBean != null && selectedProductX!!.goods_id == itemBean.goods_id
        holder.itemView.setOnClickListener {
            itemBean?.let {
                onSelectedProductX(position, itemBean)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun onSelectedProductX(position: Int, productX: ProductX) {
        selectedProductX = productX
        onItemSelectedCallback?.onItemSelected(position, productX)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
        }
    }

    interface OnItemSelectedCallback {
        fun onItemSelected(position: Int, productX: ProductX)
    }
}