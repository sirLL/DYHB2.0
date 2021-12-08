package cn.dianyinhuoban.app2.bean

data class MyMachineDYFBean(
    var arylist: List<DYFItemBean>?,
    var invitecount: String?,
    var usecount: String?
)

data class DYFItemBean(
    var act_time: String?,
    var code_no: String?,
    var id: String?,
    var image: String?,
    var model: String?,
    var name: String?,
    var qrcode_url: String?,
    var rate_id: String?
)