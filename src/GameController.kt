import entities.*

class GameController(
    mapSize : MapSize,
) {
    private val map = Map(mapSize)
    private var player : Player = Player()

    var gameState = GameState.MAP

    init{
        setPlayerCoordinates(MapPosition.CENTER)
    }

    fun checkPlayerAlive()
    {
        if(player.died())
            gameState = GameState.END
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
        map.addPlayer(player)
    }

    fun getMap(): Array<Array<MapCell>> = map.getMap()
    fun getPlayerPosition(): Pair<Int, Int> = player.getPosition()
    fun getCoordinatesByDirection(dir : Direction) : Pair<Int, Int> = player.fakeMove(dir)
    fun playerMove(pMove: Direction) {
        player.move(pMove)
        map.movePlayer(player)
        gameState = when(map.getCell(player.getPosition()).getMainEntity()) {
            is Resource -> GameState.MAP
            is Enemy -> GameState.BATTLE
            is Trader -> GameState.TRADER
            else -> GameState.MAP
        }
    }

    fun getCellInfo(cell: Pair<Int, Int>) = map.getCellInfo(cell)
    fun getPlayerInfo() = player.getInfo()
}