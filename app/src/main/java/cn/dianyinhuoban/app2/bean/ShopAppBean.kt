package cn.dianyinhuoban.app2.bean

data class ShopAppBean(
    var list: List<ShopAppItemBean>?
)

data class ShopAppItemBean(
    var id: String?,
    var image: String?,
    var msg: String?,
    var name: String?,
    var sort: String?,
    var url: String?
)