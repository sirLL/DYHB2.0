package cn.dianyinhuoban.app2.widget.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import cn.dianyinhuoban.app2.R
import cn.dianyinhuoban.app2.bean.MyMachineKJItemBean
import com.wareroom.lib_base.utils.DimensionUtils
import com.wareroom.lib_base.utils.filter.NumberFilter

class MachineTransferEditDialog : DialogFragment() {
    companion object {
        fun newInstance(machineItem: MyMachineKJItemBean): MachineTransferEditDialog {
            val args = Bundle()
            args.putParcelable("machineItem", machineItem)
            val fragment = MachineTransferEditDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private var mMachineItem: MyMachineKJItemBean? = null

    private var mConfirmCallback: OnMachineTransferConfirmCallback? = null

    public fun setOnMachineTransferConfirmCallback(callback: OnMachineTransferConfirmCallback): MachineTransferEditDialog {
        this.mConfirmCallback = callback
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMachineItem = arguments?.getParcelable("machineItem")
        val contentView = inflater.inflate(R.layout.dialog_machine_transfer, container, false)
        val quantityEdit = contentView.findViewById<EditText>(R.id.ed_count)
        quantityEdit.filters = arrayOf(NumberFilter().setDigits(0))
        contentView.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.tv_ok).setOnClickListener {
            mMachineItem?.let { item ->
                mConfirmCallback?.getTransferQuantity(
                    item,
                    quantityEdit.text.toString()
                )
            }

            dismiss()
        }
        return contentView
    }

    override fun onStart() {
        super.onStart()
        initDialog()
    }

    private fun initDialog() {
        dialog?.let {
            val screenWidth = DimensionUtils.getScreenWidth(requireContext())
            val windowWidth = screenWidth * 0.8
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = it.window?.attributes
            layoutParams?.width = windowWidth.toInt()
            dialog?.window?.attributes = layoutParams
        }
    }

    interface OnMachineTransferConfirmCallback {
        fun getTransferQuantity(machineItem: MyMachineKJItemBean, quantity: String)
    }
}