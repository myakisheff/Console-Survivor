class Map (
    private var mapSize : MapSize,
) {
    private val mapWidth : Int
    private val mapHeight : Int

    init {
        println("Размер карты: $mapSize")
        createMap()

        when(mapSize)
        {
            MapSize.SMALL -> {mapWidth = 10; mapHeight = 10}
            MapSize.MEDIUM -> {mapWidth = 15; mapHeight = 15}
            MapSize.LARGE -> {mapWidth = 20; mapHeight = 20}
        }

    }

    private fun createMap()
    {

    }

    fun getMapSize(): MapSize = mapSize

}