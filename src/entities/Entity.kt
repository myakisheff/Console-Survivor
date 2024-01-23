package entities

abstract class Entity(
    private val coordinates : Pair<Int, Int>
) {
    fun getCoordinates() = coordinates
}