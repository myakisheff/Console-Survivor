import items.EmptyEquipment
import items.Item

fun main() {
    val console = ConsoleWorker()

    println("""
    |Выберите размер карты:
    |1. Маленькая (10x10)
    |2. Средняя (15x15)
    |3. Большая (20x20)
    |Напишите номер пункта:
    """.trimMargin())

    var chosenMapSize = readln()
    var answerInt : Int = console.answerCheckerInt(chosenMapSize, 3)
    while(answerInt == -1)
    {
        println("Введите корректный номер:")
        chosenMapSize = readln()
        answerInt = console.answerCheckerInt(chosenMapSize, 3)
    }

    val mapSize : MapSize = when(answerInt)
    {
        1 -> MapSize.SMALL
        2 -> MapSize.MEDIUM
        3 -> MapSize.LARGE
        else -> MapSize.MEDIUM
    }

    val game = GameController(mapSize)

    // game start
    while(game.gameState != GameState.END)
    {
        // checking game state (in battle, trader or not)
        when(game.gameState)
        {
            GameState.MAP -> println()
            GameState.BATTLE -> {

                var battleAction : BattleAction = BattleAction.NONE
                // Player already at the cell with enemy
                while(game.gameState == GameState.BATTLE)
                {
                    battleAction = console.printBattleInfo(game.fight(battleAction))
                }
            }
            GameState.TRADER -> TODO()
            GameState.PLAYER -> {
                console.printPlayerInfo(game.getPlayerInfo())
                println("""
                |Выберите действие:
                |1. Посмотреть способности
                |2. Посмотреть инвентарь
                |3. Выйти
                |Напишите номер пункта:
                """.trimMargin())

                var chosenAction = readln()
                answerInt = console.answerCheckerInt(chosenAction, 3)
                while(answerInt == -1)
                {
                    println("Введите корректный номер:")
                    chosenAction = readln()
                    answerInt = console.answerCheckerInt(chosenAction, 3)
                }

                var afterInventoryCheck = Pair(0, EmptyEquipment("empty") as Item)
                when(answerInt)
                {
                    1 -> TODO() // check abilities
                    2 -> afterInventoryCheck = console.printInventory(game.getPlayerInventory())
                    3 -> println("leave")
                }

                when(afterInventoryCheck.first)
                {
                    1 -> game.removeItemFromInventory(afterInventoryCheck.second)
                    2 -> game.useItem(afterInventoryCheck.second)
                }
            }
            GameState.END -> continue
        }
        game.gameState = GameState.MAP

        console.printMap(game.getMap())

        println()

        println("log: Все сущности на клетке")
        console.printEntities(game.getAllEntities())
        println()

        println("Сделайте ход:")
        val directions = console.printAvailableDirections(game.getPlayerPosition(), game.getMap())
        val actCount = directions.size

        println("Введите номер действия: ")
        println("Для перехода к настройкам персонажа введите settings")

        var playerAction = readln()
        if(playerAction == "settings")
        {
            game.gameState = GameState.PLAYER
            continue
        }

        answerInt = console.answerCheckerInt(playerAction, actCount)
        while(answerInt == -1)
        {
            println("Введите корректный номер:")
            playerAction = readln()
            if(playerAction == "settings")
            {
                game.gameState = GameState.PLAYER
                continue
            }
            answerInt = console.answerCheckerInt(playerAction, actCount)
        }

        // check cell info. returns true if user really wants to go to the next cell
        val isGoing = console.printCellInfo(game.getCellInfo(
            game.getCoordinatesByDirection(directions[answerInt - 1].second)))

        // We don't need to change trader on a player
        if(isGoing && game.gameState != GameState.TRADER)
            game.playerMove(directions[answerInt - 1].second)

        game.checkPlayerAlive()
    }

    println("Игра завершена")
}
