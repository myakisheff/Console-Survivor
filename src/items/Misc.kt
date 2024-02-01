package items

class Misc(override var name: String) : Item() {
    private var count = 0
    private var maxCount = 99

    fun add(countToAdd : Int) : Boolean
    {
        if(count < maxCount)
        {
            count += countToAdd
            return true
        }
        else return false
    }

    fun remove(countToRemove : Int) : Boolean
    {
        if(count > 0)
        {
            count -= countToRemove
            if(count < 0) count = 0
            return true
        }
        else return false
    }

    fun getCount() = count
    fun getMaxCount() = maxCount
}