package cn.dianyinhuoban.app2.bean

data class MachineTransferRecordBean(
    var arylist: List<MachineTransferRecordItemBean>?,
    var count: String?
)

data class MachineTransferRecordItemBean(
    var add_time: String?,
    var id: String?,
    var num: String?,
    var partner_id: String?,
    var partner_name: String?,
    var partner_telephone: String?,
    var under_id: String?
)