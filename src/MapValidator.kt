import entities.Field

class MapValidator {
    private var availableWays : Array<Array<Boolean?>> = Array(1) { Array(1) { null } }
    private var map = Array(1) { Array(1) { MapCell(Field()) } }

    /**
     * Checks whether each cell of the map can be reached by EMPTY cells
     *
     * @property map is a two-dimensional array defining the game map.
     */
    fun allCellsAvailable(map: Array<Array<MapCell>>) : Boolean
    {
        this.map = map
        availableWays = Array(map.size) { Array(map[0].size) { false } }

        for(i in availableWays.indices)
        {
            for(j in 0..<availableWays[0].size)
            {
                if(j == 0 || i == 0 || i == availableWays.size - 1 || j == availableWays[0].size - 1)
                    availableWays[i][j] = null
            }
        }

        checkCells(Pair(1,1)) // (1, 1) because there are walls at the edge of the map

        availableWays.forEach { row ->
            row.forEach {
                if (it == false)
                    return false
            }
        }
        return true
    }

    private fun checkCells(coordinates: Pair<Int, Int>) {
        if(availableWays[coordinates.second][coordinates.first] != false)
            return

        availableWays[coordinates.second][coordinates.first] =
            if(map[coordinates.second][coordinates.first].getMainEntity() is Field)
        true else null

        for(i in 0..3)
        {
            var w = 0; var h = 0

            when(i)
            {
                0 -> h = -1
                1 -> w = -1
                2 -> h = 1
                3 -> w = 1
            }

            if(map[coordinates.second + h][coordinates.first + w].getMainEntity() is Field)
                checkCells(Pair(coordinates.first + w, coordinates.second + h))
            else availableWays[coordinates.second + h][coordinates.first + w] = null
        }
    }

    fun getWays() = availableWays
}