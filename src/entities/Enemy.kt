package entities

class Enemy(
    private val name : String,
) : Entity()
{
    private var healPoints : Int = 100
    private val damage : Int = 1
    private var defense : Int = 5
    private var isDied : Boolean = false

    fun takeDamage(dmg : Int)
    {
        healPoints -= if(dmg - defense > 0) dmg - defense else 0
        if(healPoints <= 0)
            isDied = true
    }

    fun getHP() : Int = healPoints
    fun getDamage() : Int = damage
    fun isDied() : Boolean = isDied
    fun getName() : String = name
    fun getExp() : Int {
        return (healPoints + 100 / defense + damage * 10) / 100
    }
}