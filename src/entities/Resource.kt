package entities

import SpawnFrequency

class Resource(
    private val frequency : SpawnFrequency,
    private val coordinates : Pair<Int, Int>,
) : Entity(coordinates)
{
    private val count : Int = 1
    private val isProtected : Boolean = when(frequency) {
        SpawnFrequency.LOW -> (0..4).random() != 0
        SpawnFrequency.MEDIUM -> (0..2).random() != 0
        SpawnFrequency.HIGH -> (0..1).random() != 0
    }
    private val protectionEnemy : Enemy? = if(isProtected) Enemy(0, EnemyNames.getRandom(), coordinates) else null
    fun getProtection() : Enemy? = protectionEnemy
    fun getCount() : Int = count
}