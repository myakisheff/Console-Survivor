class Resource(
    private val frequency : SpawnFrequency,
    private val coordinates : Pair<Int, Int>
)
{
    private val count : Int = 1
    private val isProtected : Boolean = when(frequency) {
        SpawnFrequency.LOW -> (0..4).random() != 0
        SpawnFrequency.MEDIUM -> (0..2).random() != 0
        SpawnFrequency.HIGH -> (0..1).random() != 0
    }
}