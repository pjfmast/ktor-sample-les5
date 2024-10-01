package avans.avd.models

interface CrudRepository<T, ID> {
    suspend fun findAll(): List<T>
    suspend fun findById(id: ID): T?
    suspend fun create(entity: T): T
    suspend fun update(entity: T)
    suspend fun delete(id: ID): Boolean
}