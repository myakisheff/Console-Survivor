package items

interface Sellable {
    var cost : Int
    fun setCost(price: Int) {
        cost = price
    }
}