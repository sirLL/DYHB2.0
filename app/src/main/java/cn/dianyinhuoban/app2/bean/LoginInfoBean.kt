package cn.dianyinhuoban.app2.bean

data class LoginInfoBean(
    var download: String?,
    var grade: Int?,
    var id: String?,
    var limit_status: Int?,
    var notice: Notice?,
    var notice_type: Int?,
    var referral: String?,
    var status: Int?,
    var telephone: String?,
    var token: String?,
    var version: String?
)

data class Notice(
    var content: String?,
    var title: String?,
    var type: Int?
)