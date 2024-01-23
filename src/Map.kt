import entities.*

class Map (
    mapSize : MapSize,
) {
    private val mapWidth : Int   // rows
    private val mapHeight : Int  // columns

    private val map: Array<Array<MapCellEntity>>
    private val resourceList : MutableList<Resource> = mutableListOf()
    private val enemyList : MutableList<Enemy> = mutableListOf()

    private var playerCoordinates = Pair(0,0)

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
        if(entity == MapCellEntity.ENEMY)
            enemyList.clear()
        else if(entity == MapCellEntity.RESOURCE)
            resourceList.clear()
    }

    fun setPlayerCoordinates(playerNewCoordinates: Pair<Int, Int>)
    {
        if(map[playerCoordinates.first][playerCoordinates.second] == MapCellEntity.PLAYER)
            map[playerCoordinates.first][playerCoordinates.second] = MapCellEntity.EMPTY
        playerCoordinates = playerNewCoordinates
        val (height, width) = playerNewCoordinates
        map[height][width] = MapCellEntity.PLAYER
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
            }
            map[y][x] = entity

            if(entity == MapCellEntity.RESOURCE)
                resourceList.add(Resource(SpawnFrequency.LOW, Pair(y,x)))
            else if (entity == MapCellEntity.ENEMY)
                enemyList.add(Enemy(i, EnemyNames.getRandom(), Pair(y,x)))
        }
    }

    fun getCell(pos : Pair<Int, Int>) = map[pos.first][pos.second]
    fun getMap() = map
    fun getMapSize() = Pair(mapWidth, mapHeight)

    fun getCellInfo(cell: Pair<Int, Int>) : Entity {

        return when(getCell(cell))
        {
            MapCellEntity.EMPTY -> Field(cell)
            MapCellEntity.RESOURCE -> resourceList.find { it.getCoordinates() == cell }!!
            MapCellEntity.ENEMY -> enemyList.find { it.getCoordinates() == cell }!!
            MapCellEntity.BARRIER ->  Barrier(cell)
            MapCellEntity.PLAYER ->  Field(cell)
            MapCellEntity.TRADER ->  Trader(cell)
        }
    }
}