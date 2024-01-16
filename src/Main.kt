fun main() {
    val console = ConsoleWorker();

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
        println("Введите корректный ответ:")
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

    val map = Map(mapSize)

    console.printMap(map.getMapSize())
}
