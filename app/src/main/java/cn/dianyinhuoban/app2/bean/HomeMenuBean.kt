package cn.dianyinhuoban.app2.bean

data class HomeMenuBean(
    var list: List<HomeMenuItemBean?>?
)

data class HomeMenuItemBean(
    var id: String?,
    var image: String?,
    var msg: String?,
    var name: String?,
    var sort: String?,
    var tag: String?,
    var type: String?,
    var url: String?
)