class Map (
    mapSize : MapSize,
) {
    private val mapWidth : Int
    private val mapHeight : Int
    private val map: Array<Array<MapCellEntity>>
    private val resourceFrequency : SpawnFrequency
    private val enemyFrequency : SpawnFrequency

    init {
        println("Размер карты: $mapSize")

        when(mapSize)
        {
            MapSize.SMALL -> {mapWidth = 10; mapHeight = 10}
            MapSize.MEDIUM -> {mapWidth = 15; mapHeight = 15}
            MapSize.LARGE -> {mapWidth = 20; mapHeight = 20}
        }

        resourceFrequency = when((0..2).random())
        {
            0 -> SpawnFrequency.LOW
            1 -> SpawnFrequency.MEDIUM
            2 -> SpawnFrequency.HIGH
            else -> SpawnFrequency.LOW
        }
        enemyFrequency = when((0..2).random())
        {
            0 -> SpawnFrequency.LOW
            1 -> SpawnFrequency.MEDIUM
            2 -> SpawnFrequency.HIGH
            else -> SpawnFrequency.LOW
        }

        println("частота ресурсов: $resourceFrequency")

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

        // init map without barriers of edges
        randomlyAddResources()
        randomlyAddBarriers()
        randomlyAddEnemies()
    }


    private fun randomlyAddResources()
    {
        val resCount : Int = when(resourceFrequency)
        {
            SpawnFrequency.LOW -> mapWidth * mapHeight / 25
            SpawnFrequency.MEDIUM -> mapWidth * mapHeight / 15
            SpawnFrequency.HIGH -> mapWidth * mapHeight / 10
        }

        for(i in 1..resCount)
        {
            // coordinates of resource
            var x = (1..<mapWidth - 1).random()
            var y = (1..<mapHeight - 1).random()

            while(map[y][x] != MapCellEntity.EMPTY)
            {
                x = (1..<mapWidth - 1).random()
                y = (1..<mapHeight - 1).random()
            }

            map[y][x] = MapCellEntity.RESOURCE
        }
    }

    private fun randomlyAddBarriers()
    {

    }

    private fun randomlyAddEnemies()
    {
        var frequency : Int = when(enemyFrequency)
        {
            SpawnFrequency.LOW -> mapWidth * mapHeight / 10
            SpawnFrequency.MEDIUM -> mapWidth * mapHeight / 5
            SpawnFrequency.HIGH -> mapWidth * mapHeight / 2
        }
    }

    fun getMap() = map
}