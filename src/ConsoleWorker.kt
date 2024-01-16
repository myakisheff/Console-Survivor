class ConsoleWorker {
    public fun answerCheckerInt(answer: String, numberOfPoints : Int) : Int
    {
        val numeric = answer.matches("-?\\d+(\\.\\d+)?".toRegex())

        if(!numeric) return -1

        val number = answer.toInt()
        if(number > numberOfPoints)
            return -1
        return number
    }

    public fun printMap(size: MapSize)
    {
        val width = when(size)
        {
            MapSize.SMALL -> 10
            MapSize.MEDIUM -> 15
            MapSize.LARGE -> 20
        }
        val height = width

    }
}