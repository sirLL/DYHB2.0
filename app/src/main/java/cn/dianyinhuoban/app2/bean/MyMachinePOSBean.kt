package cn.dianyinhuoban.app2.bean

data class MyMachinePOSBean(
    var actcount: String?,
    var allcount: String?,
    var arylist: List<MachinePOSItemBean>?,
    var count: String?,
    var regcount: String?,
    var unactcount: String?,
    var unregcount: String?
)

data class MachinePOSItemBean(
    var act_status: String?,
    var act_time: String?,
    var client: String?,
    var id: String?,
    var image: String?,
    var mobile: String?,
    var model: String?,
    var name: String?,
    var sn: String?,
    var source: String?,
    var stateid: String?
)