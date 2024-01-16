class ConsoleWorker {
    fun answerCheckerInt(answer: String, numberOfPoints : Int) : Int
    {
        val numeric = answer.matches("-?\\d+(\\.\\d+)?".toRegex())

        if(!numeric) return -1

        val number = answer.toInt()
        if(number > numberOfPoints)
            return -1
        return number
    }

    fun printMap(map: Map)
    {
        val mapToPrint = map.getMap()

        for(row in mapToPrint)
        {
            for(cell in row)
            {
                when(cell)
                {
                    MapCellEntity.EMPTY -> print("   ")
                    MapCellEntity.RESOURCE -> print(" R ")
                    MapCellEntity.ENEMY -> print(" M ")
                    MapCellEntity.BARRIER -> print(" ∎ ")
                }
            }
            println()
        }

    }
}