package entities

class EnemyNames {
    companion object {
        private val names : List<String> = listOf("Orc", "Minotaur", "Elf", "Goblin", "Basilisk", "Imp")
        fun getRandom() : String = names.random()
    }

}