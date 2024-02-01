package items

interface Sellable {
    var cost : Int
    fun setNewCost(price: Int) {
        cost = price
    }
}