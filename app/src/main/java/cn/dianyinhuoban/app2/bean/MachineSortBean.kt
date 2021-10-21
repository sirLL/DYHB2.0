package cn.dianyinhuoban.app2.bean

data class MachineSortBean(
    var list: List<MachineSortItemBean>?
)

data class MachineSortItemBean(
    var id: String?,
    var name: String?,
    var sort: String?,
    var type: String?
)