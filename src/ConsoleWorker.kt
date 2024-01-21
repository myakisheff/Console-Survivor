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

    fun printAvailableWays(ways: Array<Array<Boolean?>>)
    {
        for(row in ways)
        {
            for(cell in row)
            {
                print(" $cell ")
            }
            println()
        }
    }

    fun printMap(map: Map)
    {
        val mapToPrint = map.getMap()
        var rowNumber = 1
        var columnNumber = 1

        // show column numbers
        print("   ")
        for(row in mapToPrint)
        {
            val str = if(columnNumber < 10) "  ${columnNumber++} " else " ${columnNumber++} "
            print(str)
        }
        println()

        for(row in mapToPrint)
        {
            // show row number
            val str = if(rowNumber < 10) "${rowNumber++}  " else "${rowNumber++} "
            print(str)

            // show map row
            for(cell in row)
            {
                when(cell)
                {
                    MapCellEntity.EMPTY -> print("    ")
                    MapCellEntity.RESOURCE -> print("  R ")
                    MapCellEntity.ENEMY -> print("  M ")
                    MapCellEntity.BARRIER -> print("  ∎ ")
                    MapCellEntity.PLAYER -> print("  P ")
                }
            }
            println()
        }
    }
}