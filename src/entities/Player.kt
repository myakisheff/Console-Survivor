package entities

import Direction
import Inventory
import PlayerEquipment
import PlayerInfo
import items.*

class Player : Entity() {
    private var coordinates : Pair<Int, Int> = Pair(0,0)
    private var expPoints : Int = 0
    private var expPointsToUp : Int = 100
    private var level : Int = 1
    private var skillPoints : Int = 0

    private var resources : Int = 0

    private var healPoints : Int = 100
    private var healPointsMax : Int = 100
    private var regeneration : Int = 10
    private var damage : Int = 15
    private var defense : Int = 5
    private var mana : Int = 0
    private var manaMax : Int = 0

    private var isDied : Boolean = false

    private var prevCoordinates = coordinates

    // TODO: Spells, Abilities

    private val inventory = Inventory()
    private val equipment = PlayerEquipment()

    fun getEquipment() = equipment
    fun getInventoryItems() = inventory.getItems()

    fun putOnEquipment(newEquipment: Equipment) : Boolean
    {
        val prevEquipment : Equipment = equipment.setEquipment(newEquipment) ?: return false
        if(prevEquipment !is EmptyEquipment)
            inventory.addItem(prevEquipment)
        return true
    }

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

    private fun levelUp()
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
            if(target.isDied())
            {
                addExp(target.getExp())
            }
        }
    }

    private fun addExp(exp: Int) {
        expPoints += exp
        while (expPoints > expPointsToUp)
            levelUp()
    }

    fun takeDamage(dmg : Int)
    {
        healPoints -= if(dmg - defense > 0) dmg - defense else 0
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
            healPointsMax = healPointsMax.toString(),
            damage = damage.toString(),
            defense = defense.toString(),
            regeneration = regeneration.toString(),
            mana = mana.toString(),
            manaMax = manaMax.toString(),
            coordinates = "${coordinates.first}, ${coordinates.second}"
        )
    }

    fun statIncrease()
    {

    }

    fun died(): Boolean = isDied
    fun removeFromInventory(item: Item) {
        inventory.removeItem(item)
    }

    fun useItem(item: Item) {
        if(!inventory.getItems().contains(item))
            return

        when(item)
        {
            is Equipment -> {
                inventory.removeItem(item)
                equipment.setEquipment(item)
            }

            is Misc -> {
                inventory.removeItem(item)
                item.remove(1)
                if(item.getCount() > 0)
                    inventory.addItem(item)
            }
        }
    }
}