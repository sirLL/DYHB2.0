package cn.dianyinhuoban.app2.mvp.me.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.AddressBean
import cn.dianyinhuoban.app2.bean.AddressItemBean
import cn.dianyinhuoban.app2.event.AddressDeleteSuccessEvent
import cn.dianyinhuoban.app2.event.AddressEditSuccessEvent
import cn.dianyinhuoban.app2.mvp.me.contract.AddressContract
import cn.dianyinhuoban.app2.mvp.me.presenter.AddressPresenter
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseListFragment
import com.wareroom.lib_base.ui.adapter.SimpleAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AddressFragment : BaseListFragment<AddressItemBean, AddressContract.Presenter?>(),
    AddressContract.View {
    companion object {
        const val RC_EDIT_ADDRESS = 1

        fun newInstance(): AddressFragment {
            val args = Bundle()
            val fragment = AddressFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getPresenter(): AddressContract.Presenter? {
        return AddressPresenter(this)
    }

    override fun isSupportLoadMore(): Boolean {
        return false
    }

    override fun getItemLayout(): Int {
        return R.layout.item_address
    }

    override fun initView(contentView: View?) {
        super.initView(contentView)
        EventBus.getDefault().register(this)
    }

    override fun onRequest(page: Int) {
        mPresenter?.fetchAddress()
    }

    override fun bindAddress(addressBean: AddressBean?) {
        val data: MutableList<AddressItemBean> = mutableListOf()
        addressBean?.arylist?.let {
            for (bean in it) {
                data.add(bean)
            }
        }
        loadData(data)
    }


    override fun convert(
        viewHolder: SimpleAdapter.SimpleViewHolder?,
        position: Int,
        itemData: AddressItemBean?
    ) {
        viewHolder?.setText(R.id.tv_area, itemData?.area ?: "")
        viewHolder?.setText(R.id.tv_address, itemData?.address ?: "")
        viewHolder?.setText(R.id.tv_name, itemData?.contact ?: "")
        viewHolder?.setText(R.id.tv_phone, itemData?.mobile ?: "")

        val checkBox = viewHolder?.getView<TextView>(R.id.tv_check_box)
        checkBox?.isSelected = itemData != null && "1" == itemData.isdefault

        checkBox?.setOnClickListener {
            if (!it.isSelected && itemData != null) {
                mPresenter?.submitDefAddress(itemData.id ?: "")
            }
        }
        viewHolder?.getView<TextView>(R.id.tv_delete)?.setOnClickListener {
            itemData?.let {
                mPresenter?.deleteAddress(it.id ?: "")
            }
        }
        viewHolder?.getView<TextView>(R.id.tv_edit)?.setOnClickListener {
            itemData?.let {
                AddressEditActivity.openAddressEditActivity(this, it, RC_EDIT_ADDRESS)
            }
        }
    }

    override fun onSubmitDefAddressSuccess(addressID: String) {
        val data = mAdapter?.data
        data?.let {
            for (bean in it) {
                bean.isdefault = if (addressID == bean.id) {
                    "1"
                } else {
                    "0"
                }
            }
        }
        mAdapter?.notifyDataSetChanged()
    }

    override fun onDeleteAddressSuccess(addressID: String) {
        EventBus.getDefault().post(AddressDeleteSuccessEvent(addressID))
        showToast("删除成功")
        mRefreshLayout.autoRefresh()
    }

    override fun onItemClick(data: AddressItemBean?, position: Int) {
        data?.let {
            val intent = Intent()
            val bundle = Bundle()
            bundle.putParcelable("address", data)
            intent.putExtras(bundle)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddressEditSuccess(event: AddressEditSuccessEvent) {
        mRefreshLayout?.autoRefresh()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}