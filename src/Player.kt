class Player(
    private var coordinates : Pair<Int, Int> // height, width
) {
    private var level : Int = 1
    private var resources : Int = 0

    private var healPoints : Int = 1
    private var damage : Int = 1

    private var isDied : Boolean = false

    // TODO: Inventory, Spells, Equipment, Abilities

    fun move(direction: Direction)
    {
        coordinates = when(direction) {
            Direction.LEFT -> coordinates.copy(second = coordinates.second - 1)
            Direction.TOP -> coordinates.copy(second = coordinates.first - 1)
            Direction.RIGHT -> coordinates.copy(second = coordinates.second + 1)
            Direction.BOTTOM -> coordinates.copy(second = coordinates.first + 1)
        }
    }

    fun levelUp()
    {
        level++
    }

    fun attack(target: Any)
    {
        if(target is Enemy)
        {
            target.takeDamage(damage)
        }
    }

    fun takeDamage(dmg : Int)
    {
        healPoints -= dmg
        if(healPoints <= 0)
            isDied = true
    }

    fun setPosition(height: Int, width: Int) {
        coordinates = Pair(height, width)
    }

    fun getPosition(): Pair<Int, Int> = coordinates

}