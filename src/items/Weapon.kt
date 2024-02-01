package items

class Weapon(
    private val type : WeaponType,
    private var damage : Int = 0,
    override var name: String
) : Equipment() {
    fun getType() = type
    fun getDamage() = damage

    fun setDamage(newDamage : Int) {
        damage = newDamage
    }
}