import entities.Enemy

class Player(
    private var coordinates : Pair<Int, Int>
) {
    private var expPoints : Int = 0
    private var expPointsToUp : Int = 100
    private var level : Int = 1
    private var skillPoints : Int = 0

    private var resources : Int = 0

    private var healPoints : Int = 1
    private var damage : Int = 1
    private var defense : Int = 1

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
        skillPoints += if(level < 5) 5
        else 10
        expPoints -= expPointsToUp
        expPointsToUp += 100
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

    fun getInfo(): PlayerInfo {
        return PlayerInfo(
            level = level.toString(),
            expPoints = expPoints.toString(),
            expPointsToUp = expPointsToUp.toString(),
            skillPoints = skillPoints.toString(),
            healPoints = healPoints.toString(),
            damage = damage.toString(),
            defense = defense.toString(),
            coordinates = "${coordinates.first}, ${coordinates.second}"
        )
    }
}