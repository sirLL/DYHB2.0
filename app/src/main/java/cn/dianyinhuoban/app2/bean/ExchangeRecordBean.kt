package cn.dianyinhuoban.app2.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ExchangeRecordBean(
    var list: List<ExchangeRecordItemBean>?
)

@Parcelize
data class ExchangeRecordItemBean(
    var add_time: String?,
    var address: String?,
    var contact: String?,
    var exchange: String?,
    var goods_id: String?,
    var goods_name: String?,
    var id: String?,
    var integral: String?,
    var note: String?,
    var over_time: String?,
    var partner_id: String?,
    var send_time: String?,
    var shipment: String?,
    var state_id: String?,
    var telephone: String?,
    var type: String?,
    var goods_image: String?
) : Parcelable