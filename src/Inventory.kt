import items.Item

class Inventory {
    private val itemList : MutableList<Item> = mutableListOf()
    private var maxItems : Int = 10

    /**
     * Return false if there is no space for an item
     */

    private fun addItem(item: Item) : Boolean
    {
        if(itemList.size < maxItems)
        {
            itemList.add(item)
            return true
        }
        else return false
    }
}