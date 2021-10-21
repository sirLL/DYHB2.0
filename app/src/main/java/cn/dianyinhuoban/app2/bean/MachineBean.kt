package cn.dianyinhuoban.app2.bean

data class MachineBean(
    var delivery_time: String?,
    var delivery_way: String?,
    var partner_name: String?,
    var partner_tel: String?,
    var products: List<Product>?
)

data class Product(
    var product_list: List<ProductX>?,
    var product_name: String?
)

data class ProductX(
    var goods_id: Int?,
    var goods_name: String?,
    var icon: String?,
    var meal_name: String?,
    var meal_number: Int?,
    var meal_remark: String?,
    var unit_price: String?
)