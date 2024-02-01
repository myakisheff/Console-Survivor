import items.Item

class Inventory {
    private val itemList : MutableList<Item> = mutableListOf()
    private var maxItems : Int = 10

    /**
     * Return false if there is no space for an item
     */
    fun addItem(item: Item) : Boolean
    {
        if(itemList.size < maxItems)
        {
            itemList.add(item)
            return true
        }
        else return false
    }

    fun removeItem(item : Item)
    {
        itemList.remove(item)
    }

    fun getItems() = itemList
    fun getMaxItemsCount() = maxItems
    fun increaseMaxItems(count : Int)
    {
        maxItems += count
    }
    fun decreaseMaxItems(count : Int)
    {
        maxItems -= count

        if(maxItems < 0) maxItems = 0

        if(isItemsOverLimit())
            removeItemsUpperLimit()
    }

    private fun isItemsOverLimit() : Boolean = itemList.size > maxItems

    private fun removeItemsUpperLimit()
    {
        for(i in itemList.size - 1..<itemList.size-maxItems)
            itemList.removeAt(i)
    }
}