package avans.avd.models

import kotlinx.coroutines.runBlocking

object FakeUserRepository : UserRepository {
    private var currentId: Long = 0L
    private val users = mutableListOf<User>()

    // Seed the fake UserRepository with some dummy data
    // here runBlocking is used to bridge non-coroutine code with suspending functions, use it carefully: https://medium.com/@shivayogih25/unveiling-the-power-of-runblocking-in-kotlin-coroutines-48c3d15f78a3
    // When created an user gets a unique id
   init {
        runBlocking {
            create(User("admin", "password", "admin@avans.nl", role = Role.ADMIN))
            create(User("Henk", "pwd", "henk@avans.nl", role = Role.USER))
            create(User( "Anne", "pwd", "anne@breda.nl", role = Role.OFFICIAL))
        }
    }


    override suspend fun findByUsername(username: String): User? = users.find { it.username == username }

    override suspend fun findAll(): List<User> = users.toList()

    override suspend fun findById(id: Long): User? = users.find { it.id == id }


    override suspend fun create(entity: User): User {
        currentId++
        val newUser = entity.copy(id = currentId)
        users.add(newUser)
        return newUser
    }

    override suspend fun update(entity: User) {
        check(entity.id > 0) { "Id must be greater than 0" }
        require(users.any { it.id == entity.id })
        users.removeIf { it.id == entity.id }
        users.add(entity)
    }

    override suspend fun delete(id: Long): Boolean = users.removeIf { it.id == id }

}