package cn.dianyinhuoban.app2.event

/**
 * @see cn.dianyinhuoban.app2.mvp.me.view.AddressFragment 发送-->删除地址成功
 * @see cn.dianyinhuoban.app2.mvp.machine.view.ExchangeActivity 接收-->对比id,绑定地址
 */
class AddressDeleteSuccessEvent(addressID: String?) {
    var addressID: String? = null

    init {
        this.addressID = addressID
    }
}