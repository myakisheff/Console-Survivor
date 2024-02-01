package items

abstract class Item : Sellable
{
    override var cost: Int = 0
    abstract var name : String
}