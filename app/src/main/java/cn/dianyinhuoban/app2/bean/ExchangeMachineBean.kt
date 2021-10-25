package cn.dianyinhuoban.app2.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ExchangeMachineBean(
    var list: List<ExchangeMachineItemBean>?
)

@Parcelize
data class ExchangeMachineItemBean(
    var favorable_name: String?,
    var favorable_num: Int?,
    var favorable_remark: String?,
    var id: String?,
    var image: String?,
    var integral: String?,
    var model: String?,
    var name: String?,
    var price: String?,
    var type: Int?
) : Parcelable