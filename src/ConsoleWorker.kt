import entities.*

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

    fun printMap(map: Array<Array<MapCellEntity>>)
    {
        var rowNumber = 0
        var columnNumber = 0

        // show column numbers
        print("   ")
        for(row in map)
        {
            val str = if(columnNumber < 10) "  ${columnNumber++} " else " ${columnNumber++} "
            print(str)
        }
        println()

        for(row in map)
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
                    MapCellEntity.TRADER -> print("  T ")
                }
            }
            println()
        }
    }

    fun printAvailableDirections(playerPos : Pair<Int, Int>, map: Array<Array<MapCellEntity>>) : MutableList<Pair<String, Direction>> {
        val directions : MutableList<Pair<String, Direction>> = mutableListOf()

        val (height, width) = playerPos

        for(i in 0..3)
        {
            var w  = 0
            var h  = 0

            var dirTxt = ""
            var dir = Direction.NONE

            when(i)
            {
                0 -> { w = 1; dirTxt = "на право"; dir = Direction.RIGHT}
                1 -> { w = -1; dirTxt = "на лево"; dir = Direction.LEFT}
                2 -> { h = 1; dirTxt = "вниз"; dir = Direction.BOTTOM}
                3 -> { h = -1; dirTxt = "вверх"; dir = Direction.TOP}
            }

            when(map[height + h][width + w])
            {
                MapCellEntity.EMPTY -> directions.add(Pair("Перейти ($dirTxt)", dir))
                MapCellEntity.RESOURCE -> directions.add(Pair("Подобрать ресурсы ($dirTxt)", dir))
                MapCellEntity.ENEMY -> directions.add(Pair("Атаковать ($dirTxt)", dir))
                MapCellEntity.BARRIER -> continue
                MapCellEntity.PLAYER -> continue
                MapCellEntity.TRADER -> directions.add(Pair("Торговать ($dirTxt)", dir))
            }
        }

        var n = 1
        directions.forEach {
            println("$n. ${it.first}")
            n++
        }

        return directions
    }

    fun printCellInfo(cellEntity: Entity) : Boolean {
        when(cellEntity)
        {
            is Enemy -> {
                println("Информация о монстре:")
                println("Имя: ${cellEntity.getName()}")
                println("Здоровье: ${cellEntity.getHP()}")
                println("Урон: ${cellEntity.getDamage()}")
            }
            is Resource -> {
                println("Информация о ресурсах:")
                println("Защита: ${if(cellEntity.getProtection() == null) "Нет" else "Есть"}")
                println("Количество: ${cellEntity.getCount()}")
            }
            is Trader -> {
                println("Информация о торговце:")
                TODO("implement trader")
                // ready to trade or not
            }
            is Field -> return true
            is Barrier -> return false
        }
        println()
        println("""
        |Перейти?
        |1. Да
        |2. Нет
        |Напишите номер пункта:
        """.trimMargin())

        var goOrNot = readln()
        var answerInt : Int = answerCheckerInt(goOrNot, 2)
        while(answerInt == -1)
        {
            println("Введите корректный номер:")
            goOrNot = readln()
            answerInt = answerCheckerInt(goOrNot, 2)
        }

        when(answerInt)
        {
            1 -> println("Переходим...")
            2 -> println("Стоим на месте")
        }

        return answerInt == 1
    }

}