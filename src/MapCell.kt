import entities.Entity
import entities.Field

class MapCell(
    private var mainEntity : Entity
) {
    private var entities : MutableList<Entity> = mutableListOf()

    fun addEntity(entity: Entity)
    {
        entities.add(entity)
        if(entities.size == 1)
            mainEntity = entities[0]
    }

    fun removeEntity(entity: Entity)
    {
        entities.remove(entity)
        mainEntity = if(entities.size == 0) {
            Field()
        } else entities[0]
    }

    fun removeAllEntities()
    {
        entities.clear()
        mainEntity = Field()
    }

    fun getMainEntity() = mainEntity
}