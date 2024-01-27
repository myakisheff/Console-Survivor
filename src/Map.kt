import entities.*

class Map (
    mapSize : MapSize,
) {
    private val mapWidth : Int   // rows
    private val mapHeight : Int  // columns

    private val map: Array<Array<MapCell>>
    private val resourceList : MutableList<Resource> = mutableListOf()
    private val enemyList : MutableList<Enemy> = mutableListOf()

    private var playerCoordinates = Pair(0, 0)

    private var mapValidator = MapValidator()

    init {
        when(mapSize)
        {
            MapSize.SMALL -> {mapWidth = 10; mapHeight = 10}
            MapSize.MEDIUM -> {mapWidth = 15; mapHeight = 15}
            MapSize.LARGE -> {mapWidth = 20; mapHeight = 20}
        }

        map = Array(mapHeight) { Array(mapWidth) { MapCell(Field()) } }

        createMap()
    }

    private fun createMap() {
        // adding barriers to the edges of the map
        for(i in 0..<mapHeight)
        {
            if(i == 0 || i == mapHeight - 1)
            {
                map[i] = Array(mapWidth) { MapCell(Barrier()) }
                continue
            }

            for(j in 0..<mapWidth)
            {
                if(j == 0 || j == mapWidth - 1)
                    map[i][j] = MapCell(Barrier())
            }
        }

        do
        {
            deleteInnerEntities()

            randomlyAddEntity(MapCellEntity.BARRIER)
            randomlyAddEntity(MapCellEntity.RESOURCE)
            randomlyAddEntity(MapCellEntity.ENEMY)
        }while(!mapValidator.allCellsAvailable(map))

    }

    private fun deleteInnerEntities()
    {
        for(i in 1..<mapHeight - 1)
        {
            for(j in 1..<mapWidth - 1)
            {
                map[i][j].removeAllEntities()
            }
        }

        enemyList.clear()
        resourceList.clear()
    }

    fun addPlayer(player: Player)
    {
        val (pHeight, pWidth) = player.getPosition()
        map[pHeight][pWidth].addEntity(player)
        playerCoordinates = Pair(pHeight, pWidth)
    }

    fun movePlayer(player: Player)
    {
        val (pHeight, pWidth) = player.getPosition()
        val (prevPHeight, prevPWidth) = playerCoordinates
        map[prevPHeight][prevPWidth].removeEntity(player)
        map[pHeight][pWidth].addEntity(player)
        playerCoordinates = Pair(pHeight, pWidth)
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

            while(map[y][x].getMainEntity() !is Field)
            {
                x = (1..<mapWidth - 1).random()
                y = (1..<mapHeight - 1).random()
            }

            val ent = when(entity)
            {
                MapCellEntity.EMPTY -> Field()
                MapCellEntity.RESOURCE -> Resource()
                MapCellEntity.ENEMY -> Enemy(EnemyNames.getRandom())
                MapCellEntity.BARRIER -> Barrier()
                MapCellEntity.PLAYER -> Player()
                MapCellEntity.TRADER -> Trader()
            }

            map[y][x].addEntity(ent)

            if(ent is Resource)
                resourceList.add(ent)
            else if (ent is Enemy)
                enemyList.add(ent)
        }
    }

    fun getCell(pos : Pair<Int, Int>) = map[pos.first][pos.second]
    fun getMap() = map
    fun getMapSize() = Pair(mapWidth, mapHeight)

    fun getCellInfo(cell: Pair<Int, Int>) = getCell(cell).getMainEntity()
    fun getAllEntities(cell: Pair<Int, Int>) = map[cell.first][cell.second].getAllEntities()
}