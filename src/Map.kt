class Map (
    mapSize : MapSize,
) {
    private val mapWidth : Int
    private val mapHeight : Int
    private val map: Array<Array<MapCellEntity>>

    init {
        println("Размер карты: $mapSize")

        when(mapSize)
        {
            MapSize.SMALL -> {mapWidth = 10; mapHeight = 10}
            MapSize.MEDIUM -> {mapWidth = 15; mapHeight = 15}
            MapSize.LARGE -> {mapWidth = 20; mapHeight = 20}
        }

        map = Array(mapHeight) { Array(mapWidth) { MapCellEntity.EMPTY } }

        createMap()
    }

    private fun createMap() {

        // adding barriers to the edges of the map
        for(i in 0..<mapHeight)
        {
            if(i == 0 || i == mapHeight - 1)
            {
                map[i] = Array(mapWidth) { MapCellEntity.BARRIER }
                continue
            }

            for(j in 0..<mapWidth)
            {
                if(j == 0 || j == mapWidth - 1)
                    map[i][j] = MapCellEntity.BARRIER
            }
        }


    }



    fun getMap() = map
}