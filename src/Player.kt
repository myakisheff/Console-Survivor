import entities.Enemy

class Player(
    private var coordinates : Pair<Int, Int>
) {
    private var level : Int = 1
    private var resources : Int = 0

    private var healPoints : Int = 1
    private var damage : Int = 1

    private var isDied : Boolean = false

    private var prevCoordinates = coordinates

    // TODO: Inventory, Spells, Equipment, Abilities

    fun move(direction: Direction)
    {
        prevCoordinates = coordinates
        coordinates = when(direction) {
            Direction.LEFT -> coordinates.copy(second = coordinates.second - 1)
            Direction.TOP -> coordinates.copy(first = coordinates.first - 1)
            Direction.RIGHT -> coordinates.copy(second = coordinates.second + 1)
            Direction.BOTTOM -> coordinates.copy(first = coordinates.first + 1)
            Direction.NONE -> coordinates
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
        prevCoordinates = coordinates
        coordinates = Pair(height, width)
    }

    fun getPosition(): Pair<Int, Int> = coordinates
    fun fakeMove(dir: Direction): Pair<Int, Int> {
        return when(dir) {
            Direction.LEFT -> coordinates.copy(second = coordinates.second - 1)
            Direction.TOP -> coordinates.copy(first = coordinates.first - 1)
            Direction.RIGHT -> coordinates.copy(second = coordinates.second + 1)
            Direction.BOTTOM -> coordinates.copy(first = coordinates.first + 1)
            Direction.NONE -> coordinates
        }
    }

}