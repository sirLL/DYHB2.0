package cn.dianyinhuoban.app2.mvp.machine.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.ExchangeMachineBean
import cn.dianyinhuoban.app2.bean.ExchangeMachineItemBean
import cn.dianyinhuoban.app2.mvp.machine.contract.ExchangeMachinePickerContract
import cn.dianyinhuoban.app2.mvp.machine.presenter.ExchangeMachinePickerPresenter
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter

class ExchangeMachinePickerFragment :
    BaseListFragment<ExchangeMachineItemBean, ExchangeMachinePickerContract.Presenter?>(),
    ExchangeMachinePickerContract.View {

    companion object {
        fun newInstance(selectedID: String?): ExchangeMachinePickerFragment {
            val args = Bundle()
            args.putString("selectedID", selectedID)
            val fragment = ExchangeMachinePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView(contentView: View?) {
        selectedID = arguments?.getString("selectedID", "")
        super.initView(contentView)
    }

    private var selectedID: String? = null

    override fun getPresenter(): ExchangeMachinePickerContract.Presenter? {
        return ExchangeMachinePickerPresenter(this)
    }

    override fun onRequest(page: Int) {
        fetchExchangeMachine(page, DEF_PAGE_SIZE)
    }

    override fun getItemLayout(): Int {
        return R.layout.item_exchange_machine_picker
    }

    private fun fetchExchangeMachine(page: Int, pageSize: Int) {
        mPresenter?.fetchExchangeMachine(page, pageSize)
    }

    override fun bindExchangeMachine(machineBean: ExchangeMachineBean?) {
        val data = mutableListOf<ExchangeMachineItemBean>()
        machineBean?.list?.let {
            for (itemBean in it) {
                data.add(itemBean)
            }
        }
        loadData(data)
    }

    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: ExchangeMachineItemBean?
    ) {
        viewHolder?.getView<View>(R.id.divider)?.visibility = if (position == 0) {
            View.GONE
        } else {
            View.VISIBLE
        }
        viewHolder?.setText(R.id.tv_title, itemData?.name ?: "--")
        viewHolder?.setText(R.id.tv_price, "${itemData?.price ?: "--"}积分")
        val ivCover: RoundedImageView? = viewHolder?.getView(R.id.iv_cover)
        ivCover?.let {
            Glide.with(it).load(itemData?.image ?: "").into(it)
        }

        val ivCheckBox = viewHolder?.getView<ImageView>(R.id.iv_check_box)
        if (selectedID.isNullOrEmpty()) {
            ivCheckBox?.isSelected = false
        } else {
            ivCheckBox?.isSelected = selectedID == itemData?.id
        }

    }

    override fun onItemClick(data: ExchangeMachineItemBean?, position: Int) {
        data?.let {
            val intent = Intent()
            val bundle = Bundle()
            bundle.putParcelable("selectedMachine", data)
            intent.putExtras(bundle)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }
}