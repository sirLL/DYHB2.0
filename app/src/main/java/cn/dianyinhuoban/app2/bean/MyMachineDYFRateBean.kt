package cn.dianyinhuoban.app2.bean

data class MyMachineDYFRateBean(
    var rate: Rate?
)

data class Rate(
    var credit: String?,
    var credit_service: String?,
    var debit: String?,
    var debit_service: String?,
    var end_time: String?,
    var id: String?,
    var large_credit: String?,
    var large_credit_service: String?,
    var large_debit: String?,
    var large_debit_service: String?,
    var qrcode: String?,
    var qrcode_service: String?,
    var sing_trade_max: String?,
    var start_time: String?,
    var total_limit: String?
)