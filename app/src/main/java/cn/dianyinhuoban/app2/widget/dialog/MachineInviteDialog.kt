package cn.dianyinhuoban.app2.widget.dialog

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import cn.dianyinhuoban.app2.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hjq.toast.ToastUtils

class MachineInviteDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(qr: String, url: String): MachineInviteDialog {
            val args = Bundle()
            args.putString("qr", qr)
            args.putString("url", url)
            val fragment = MachineInviteDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private var mQRCode: String? = null
    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mQRCode = arguments?.getString("qr", "")
        mUrl = arguments?.getString("url", "")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = inflater.inflate(R.layout.dialog_machine_invite, container, false)
        contentView.findViewById<TextView>(R.id.tv_invite_qr).setOnClickListener {

        }
        contentView.findViewById<TextView>(R.id.tv_invite_url).setOnClickListener {
            val cm = getSystemService(requireContext(), ClipboardManager::class.java)
            val clipData = ClipData.newPlainText(null, mUrl)
            cm?.setPrimaryClip(clipData)
            ToastUtils.show("复制链接成功")
            dismiss()
        }
        contentView.findViewById<ImageView>(R.id.iv_cancel).setOnClickListener {
            dismiss()
        }
        return contentView
    }
}