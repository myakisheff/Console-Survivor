class GameController(
    mapSize : MapSize,
) {
    private val map = Map(mapSize)
    private var player : Player = Player(Pair(0,0))

    var gameState = GameState.MAP

    init{
        setPlayerCoordinates(MapPosition.CENTER)
    }

    private fun setPlayerCoordinates(position: MapPosition)
    {
        val (mapHeight, mapWidth) = map.getMapSize()

        val playerCoordinates = when(position) {
            MapPosition.LEFT_TOP_CORNER -> Pair(1, 1)
            MapPosition.RIGHT_TOP_CORNER -> Pair(1, mapWidth - 1)
            MapPosition.CENTER -> Pair(mapHeight / 2, mapWidth / 2)
            MapPosition.LEFT_BOTTOM_CORNER -> Pair(mapHeight - 1, 1)
            MapPosition.RIGHT_BOTTOM_CORNER -> Pair(mapHeight - 1, mapWidth - 1)
        }

        val (height, width) = playerCoordinates
        player.setPosition(height, width)
        map.setPlayerCoordinates(player.getPosition())
    }

    fun getMap(): Array<Array<MapCellEntity>> = map.getMap()
    fun getPlayerPosition(): Pair<Int, Int> = player.getPosition()
    fun getCoordinatesByDirection(dir : Direction) : Pair<Int, Int> = player.fakeMove(dir)
    fun playerMove(pMove: Direction) {
        player.move(pMove)
        map.setPlayerCoordinates(player.getPosition())
        gameState = when(map.getCell(player.getPosition())) {
            MapCellEntity.EMPTY -> GameState.MAP
            MapCellEntity.RESOURCE -> GameState.MAP
            MapCellEntity.ENEMY -> GameState.BATTLE
            MapCellEntity.BARRIER -> GameState.MAP
            MapCellEntity.PLAYER -> GameState.MAP
            MapCellEntity.TRADER -> GameState.TRADER
        }
    }

    fun getCellInfo(cell: Pair<Int, Int>) = map.getCellInfo(cell)
    fun getPlayerInfo() = player.getInfo()
}