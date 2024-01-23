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

    while(game.gameState != GameState.END)
    {
        when(game.gameState)
        {
            GameState.MAP -> println()
            GameState.BATTLE -> TODO()
            GameState.TRADER -> TODO()
            GameState.PLAYER -> TODO()
            GameState.END -> continue
        }


        console.printMap(game.getMap())

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

        // check cell info
        val isGoing = console.printCellInfo(game.getCellInfo(
            game.getCoordinatesByDirection(directions[answerInt - 1].second)))

        if(isGoing && game.gameState != GameState.TRADER)
            game.playerMove(directions[answerInt - 1].second)
    }
}
