class Map (
    mapSize : MapSize,
) {
    private val mapWidth : Int   // rows
    private val mapHeight : Int  // columns

    private val map: Array<Array<MapCellEntity>>
    private val resourceList : MutableList<Resource> = mutableListOf()
    private val enemyList : MutableList<Enemy> = mutableListOf()

    private var playerSpawnCoordinates : Pair<Int, Int> = Pair(0,0)
    private var player : Player = Player(Pair(0,0))

    private var mapValidator = MapValidator()

    init {
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

        setPlayerSpawnCoordinates(MapPosition.LEFT_TOP_CORNER)

        do
        {
            deleteInnerEntity(MapCellEntity.BARRIER)
            deleteInnerEntity(MapCellEntity.RESOURCE)
            deleteInnerEntity(MapCellEntity.ENEMY)

            randomlyAddEntity(MapCellEntity.BARRIER)
            randomlyAddEntity(MapCellEntity.RESOURCE)
            randomlyAddEntity(MapCellEntity.ENEMY)
        }while(!mapValidator.allCellsAvailable(map))

    }

    private fun deleteInnerEntity(entity: MapCellEntity)
    {
        for(i in 1..<mapHeight - 1)
        {
            for(j in 1..<mapWidth - 1)
            {
                if (map[i][j] == entity)
                    map[i][j] = MapCellEntity.EMPTY
            }
        }
    }

    private fun setPlayerSpawnCoordinates(position: MapPosition)
    {
        playerSpawnCoordinates = when(position) {
            MapPosition.LEFT_TOP_CORNER -> Pair(1, 1)
            MapPosition.RIGHT_TOP_CORNER -> Pair(1, mapWidth - 1)
            MapPosition.CENTER -> Pair(mapHeight / 2, mapWidth / 2)
            MapPosition.LEFT_BOTTOM_CORNER -> Pair(mapHeight - 1, 1)
            MapPosition.RIGHT_BOTTOM_CORNER -> Pair(mapHeight - 1, mapWidth - 1)
        }

        val (width, height) = playerSpawnCoordinates
        map[height][width] = MapCellEntity.PLAYER
        player.setPosition(height, width)
    }

    private fun randomlyAddEntity(entity: MapCellEntity)
    {
        val frequency = when((0..2).random())
        {
            0 -> SpawnFrequency.LOW
            1 -> SpawnFrequency.MEDIUM
            2 -> SpawnFrequency.HIGH
            else -> SpawnFrequency.LOW
        }

        val count : Int = when(frequency)
        {
            SpawnFrequency.LOW -> mapWidth * mapHeight / 25
            SpawnFrequency.MEDIUM -> mapWidth * mapHeight / 15
            SpawnFrequency.HIGH -> mapWidth * mapHeight / 10
        }

        for(i in 1..count)
        {
            // coordinates
            var x = (1..<mapWidth - 1).random()
            var y = (1..<mapHeight - 1).random()

            while(map[y][x] != MapCellEntity.EMPTY)
            {
                x = (1..<mapWidth - 1).random()
                y = (1..<mapHeight - 1).random()

                if(entity == MapCellEntity.RESOURCE)
                    resourceList.add(Resource(SpawnFrequency.LOW, Pair(x,y)))
                else if (entity == MapCellEntity.ENEMY)
                    enemyList.add(Enemy(EnemyNames.getRandom(), Pair(x,y)))

            }
            map[y][x] = entity
        }
    }

    fun getMap() = map
    fun getCell(height : Int, width : Int) = map[height][width]
    fun getPlayerPosition(): Pair<Int, Int> = player.getPosition()
}