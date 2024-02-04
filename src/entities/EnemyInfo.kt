package entities

data class EnemyInfo (
    val name : String,
    val healPoints : String,
    val healPointsMax : String,
    val damage : String,
    val defense : String,
) {
    companion object {
        fun noEnemy(): EnemyInfo {
            return EnemyInfo(
                "FALSE",
                "FALSE",
                "FALSE",
                "FALSE",
                "FALSE"
            )
        }
    }
}