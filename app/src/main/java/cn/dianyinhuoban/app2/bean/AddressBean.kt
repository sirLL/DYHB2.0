package cn.dianyinhuoban.app2.bean

data class AddressBean(
    var arylist: List<AddressItemBean>?,
    var count: Int?
)

data class AddressItemBean(
    var address: String?,
    var area: String?,
    var contact: String?,
    var id: String?,
    var isdefault: String?,
    var mobile: String?
)