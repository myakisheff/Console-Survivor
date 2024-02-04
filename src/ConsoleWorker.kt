import entities.*
import items.EmptyEquipment
import items.Item

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

    fun printMap(map: Array<Array<MapCell>>)
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
                when(cell.getMainEntity())
                {
                    is Field -> print("    ")
                    is Resource -> print("  R ")
                    is Enemy -> print("  M ")
                    is Barrier -> print("  ∎ ")
                    is Player -> print("  P ")
                    is Trader -> print("  T ")
                }
            }
            println()
        }
    }

    fun printAvailableDirections(playerPos : Pair<Int, Int>, map: Array<Array<MapCell>>) : MutableList<Pair<String, Direction>> {
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

            when(map[height + h][width + w].getMainEntity())
            {
                is Field -> directions.add(Pair("Перейти ($dirTxt)", dir))
                is Resource -> directions.add(Pair("Подобрать ресурсы ($dirTxt)", dir))
                is Enemy -> directions.add(Pair("Атаковать ($dirTxt)", dir))
                is Trader -> directions.add(Pair("Торговать ($dirTxt)", dir))
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
                println("Здоровье: ${cellEntity.getHP()}/${cellEntity.getMaxHP()}")
                println("Урон: ${cellEntity.getDamage()}")
                println("Защита: ${cellEntity.getDefense()}")
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

    fun printPlayerInfo(playerInfo: PlayerInfo) {
        println("\n-------------------------------------")
        // print player info
        println("Уровень: ${playerInfo.level}")
        println("Опыт: ${playerInfo.expPoints}/${playerInfo.expPointsToUp}")
        println("Очки способностей: ${playerInfo.skillPoints}")
        println("Здоровье: ${playerInfo.healPoints}/${playerInfo.healPointsMax}")
        println("Регенерация: ${playerInfo.healPoints}/ход")
        println("Урон: ${playerInfo.damage}")
        println("Защита: ${playerInfo.defense}")
        println("Мана: ${playerInfo.mana}/${playerInfo.manaMax}")
        println("-------------------------------------\n")
    }

    fun printEntities(allEntities: MutableList<Entity>) {
        allEntities.forEach {
            println("log: entity: $it")
        }
    }

    fun printInventory(playerInventory: MutableList<Item>) : Pair<Int,Item> {
        println()
        println("Инвентарь: ")
        var num = 1
        playerInventory.forEach {
            println("$num. $it")
            num++
        }
        println()

        println("Введите номер предмета для действия или 0 для выхода")

        var itemOrExit = readln()
        var answerInt : Int = answerCheckerInt(itemOrExit, playerInventory.size)
        while(answerInt == -1)
        {
            println("Введите корректный номер:")
            itemOrExit = readln()
            answerInt = answerCheckerInt(itemOrExit, playerInventory.size)
        }

        when(answerInt)
        {
            0 -> println("Возврат...")
            else -> return printItem(playerInventory[answerInt])
        }
        return Pair(0, EmptyEquipment("empty"))
    }

    private fun printItem(item: Item) : Pair<Int,Item> {
        println()
        println("Предмет: ${item.name}")
        println()

        println("""
        |Введите номер действия
        |1. Выбросить
        |2. Использовать
        |3. Выход
        |Напишите номер пункта:
        """.trimMargin())

        var itemOrExit = readln()
        var answerInt : Int = answerCheckerInt(itemOrExit, 3)
        while(answerInt == -1)
        {
            println("Введите корректный номер:")
            itemOrExit = readln()
            answerInt = answerCheckerInt(itemOrExit, 3)
        }

        when(answerInt)
        {
            1 -> {
                println("Выбрасываем ${item.name}...")
                return Pair(1, item)
            }
            2 -> {
                return Pair(2, item)
            }
            else -> return Pair(0, EmptyEquipment("empty"))
        }
    }

    fun printBattleInfo(fight: Pair<PlayerInfo, EnemyInfo>): BattleAction {
        val (player, enemy) = fight
        println("Текущее состояние битвы:")
        println("""
        | Вы VS ${enemy.name}
        |
        |Ваше здоровье: ${player.healPoints}/${player.healPointsMax}
        |Здоровье противника: ${enemy.healPoints}/${enemy.healPointsMax}
        |
        |Ваш урон: ${player.damage}
        |Урон противника: ${enemy.damage}
        |
        |Ваша защита: ${player.defense}
        |Защита противника: ${player.defense}
        |
        |Ваша мана: ${player.mana}
        """.trimMargin())

        println("""
        |Ваши действия?
        |1. Напасть
        |2. Использовать заклинание
        |3. Защищаться
        |Напишите номер пункта:
        """.trimMargin())

        var battleAction = readln()
        var answerInt : Int = answerCheckerInt(battleAction, 3)
        while(answerInt == -1)
        {
            println("Введите корректный номер:")
            battleAction = readln()
            answerInt = answerCheckerInt(battleAction, 3)
        }

        return when(answerInt) {
            1 -> BattleAction.ATTACK
            2 -> BattleAction.SPELL
            3 -> BattleAction.DEFENSE
            else -> BattleAction.NONE
        }
    }

}