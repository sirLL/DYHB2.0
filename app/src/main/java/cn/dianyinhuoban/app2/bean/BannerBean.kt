package cn.dianyinhuoban.app2.bean

class BannerBean(
  public  var arylist: List<BannerItemBean>?,
  public var count: Int?
)

class BannerItemBean(
    var image: String?,
    var type: Int?,
    var url: String?
)