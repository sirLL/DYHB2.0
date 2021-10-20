package cn.dianyinhuoban.app2.widget.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.DialogFragment
import cn.dianyinhuoban.app2.R
import com.wareroom.lib_base.utils.DimensionUtils
import com.wareroom.lib_base.utils.OSUtils

class SetRankDialog : DialogFragment() {
    companion object {
        fun newInstance(phone: String?): SetRankDialog {
            val args = Bundle()
            args.putString("phone", phone)
            val fragment = SetRankDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private var phone: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       phone= arguments?.getString("phone","")

        val contentView = inflater.inflate(R.layout.dialog_setrank, container, false)
        contentView.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            OSUtils.callPhone(requireContext(), phone)
            dismiss()
        }
        return contentView
    }

    override fun onStart() {
        super.onStart()
        initWindow()
    }

    private fun initWindow() {
        dialog?.let {
            val screenWidth = DimensionUtils.getScreenWidth(requireContext())
            val windowWidth = screenWidth * 0.8
            val windowHeight = windowWidth * (887.0 / 813.0)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = it.window?.attributes
            layoutParams?.width = windowWidth.toInt()
            layoutParams?.height = windowHeight.toInt()
            dialog?.window?.attributes = layoutParams
        }

    }
}