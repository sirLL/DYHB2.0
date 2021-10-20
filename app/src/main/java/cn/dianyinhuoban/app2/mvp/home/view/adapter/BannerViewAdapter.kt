package cn.dianyinhuoban.app2.mvp.home.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cn.dianyinhuoban.app2.api.URLConfig
import cn.dianyinhuoban.app2.bean.BannerBean
import cn.dianyinhuoban.app2.bean.BannerItemBean
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class BannerViewAdapter(data: MutableList<BannerItemBean?>, bannerRound: Int) :
    BannerAdapter<BannerItemBean, BannerViewAdapter.BannerViewHolder>(data) {
    private var bannerRound = bannerRound
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent!!.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //通过裁剪实现圆角
        if (bannerRound > 0) {
            BannerUtils.setBannerRound(imageView, 20f)
        }
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: BannerItemBean?,
        position: Int,
        size: Int
    ) {
        holder?.itemView?.let {
            Glide.with(it)
                .load(URLConfig.appendHostURL(data?.image ?: ""))
                .into(it as ImageView)
        }
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}