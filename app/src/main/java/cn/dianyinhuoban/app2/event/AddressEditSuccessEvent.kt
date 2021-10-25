package cn.dianyinhuoban.app2.event

import cn.dianyinhuoban.app2.bean.AddressItemBean

/**
 * @see cn.dianyinhuoban.app2.mvp.me.view.AddressEditActivity 发送-->修改新增地址成功
 * @see cn.dianyinhuoban.app2.mvp.me.view.AddressFragment 接收-->刷新数据
 * @see cn.dianyinhuoban.app2.mvp.machine.view.ExchangeActivity 接收-->对比id,修改地址信息
 */
class AddressEditSuccessEvent(address: AddressItemBean) {
    var address: AddressItemBean? = null

    init {
        this.address = address
    }
}