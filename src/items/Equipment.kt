package items

abstract class Equipment : Item() {
    private var currentEndurance : Int = 100
    private var endurance : Int = currentEndurance

    fun destruction(points: Int)
    {
        currentEndurance -= points
        if(currentEndurance < 0)
            currentEndurance = 0
    }
    fun repair(points : Int)
    {
        currentEndurance += points
        if(currentEndurance > endurance)
            currentEndurance = endurance
    }
}