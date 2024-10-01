package avans.avd.models

interface UserRepository: CrudRepository<User, Long> {
    suspend fun findByUsername(username: String): User?
}