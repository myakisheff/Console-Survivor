import items.*

class PlayerEquipment {

    // Armor
    private var helmet : Equipment = EmptyEquipment("empty")
    private var breastplate : Equipment = EmptyEquipment("empty")
    private var boots : Equipment = EmptyEquipment("empty")

    // Weapon
    private var weapon : Equipment = EmptyEquipment("empty")

    // TODO: other wearable items

    fun getHelmet() = helmet
    fun getBreastplate() = breastplate
    fun getBoots() = boots
    fun getWeapon() = weapon

    fun setEquipment(newEquipment : Equipment) : Equipment?{
        var prevEquipment : Equipment? = null
        when(newEquipment)
        {
            is Helmet -> {
                prevEquipment = helmet
                helmet = newEquipment
            }
            is Breastplate -> {
                prevEquipment = breastplate
                breastplate = newEquipment
            }
            is Boots -> {
                prevEquipment = boots
                boots = newEquipment
            }
            is Weapon -> {
                prevEquipment = weapon
                weapon = newEquipment
            }
        }

        return prevEquipment
    }
}