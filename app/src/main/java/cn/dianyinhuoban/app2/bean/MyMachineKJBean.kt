package cn.dianyinhuoban.app2.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MyMachineKJBean(
    var arylist: List<MyMachineKJItemBean>?,
    var count: String?,
    var partnerVfSum: String?,
    var partner_name: String?,
    var partner_tel: String?
)

@Parcelize
data class MyMachineKJItemBean(
    var accountName: String?,
    var accountNo: String?,
    var act_time: String?,
    var chMerCode: String?,
    var dabiao_thousand: String?,
    var dabiao_wan: String?,
    var id: String?,
    var idCard: String?,
    var inputtime: String?,
    var member_partnerVfSum: String?,
    var merAddress: String?,
    var merCode: String?,
    var merDis: String?,
    var merName: String?,
    var mobile: String?,
    var partner_id: String?,
    var realName: String?,
    var reservedMobile: String?,
    var settleAccType: String?,
    var status: String?,
    var subBankCode: String?,
    var thousand_time: String?,
    var total_trade: String?,
    var uid: String?,
    var wan_time: String?
) : Parcelable