class Enemy(
    private val name : String,
    private val coordinates : Pair<Int, Int>
)
{
    private var healPoints : Int = 100
    private val damage : Int = 0
    private var isDied : Boolean = false

    fun takeDamage(dmg : Int)
    {
        healPoints -= dmg
        if(healPoints <= 0)
            isDied = true
    }

    fun getHP() : Int = healPoints
    fun getDamage() : Int = damage
    fun getIsDied() : Boolean = isDied
    fun getName() : String = name
}