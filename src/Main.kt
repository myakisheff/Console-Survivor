fun main() {
    println("""
    |Выберите размер карты:
    |1. Маленькая (10x10)
    |2. Средняя (15x15)
    |3. Большая (20x20)
    |Напишите номер пункта:
    """.trimMargin())

    var chosenMapSize = readln()
    var answerInt : Int = consoleAnswerCheckerInt(chosenMapSize, 3)
    while(answerInt == -1)
    {
        println("Введите корректный ответ:")
        chosenMapSize = readln()
        answerInt = consoleAnswerCheckerInt(chosenMapSize, 3)
    }

    val mapSize : MapSize = when(answerInt)
    {
        1 -> MapSize.SMALL
        2 -> MapSize.MEDIUM
        3 -> MapSize.LARGE
        else -> MapSize.MEDIUM
    }

    val map = Map(mapSize)

}

fun consoleAnswerCheckerInt(answer: String, numberOfPoints : Int) : Int
{
    val numeric = answer.matches("-?\\d+(\\.\\d+)?".toRegex())

    if(!numeric) return -1

    val number = answer.toInt()
    if(number > numberOfPoints)
        return -1
    return number
}
