package cn.dianyinhuoban.app2.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AddressBean(
    var arylist: List<AddressItemBean>?,
    var count: Int?
)

@Parcelize
data class AddressItemBean(
    var address: String?,
    var area: String?,
    var contact: String?,
    var id: String?,
    var isdefault: String?,
    var mobile: String?
):Parcelable