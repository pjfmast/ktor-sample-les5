package avans.avd.routes

import avans.avd.models.User
import avans.avd.models.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

// when business logic is needed: routing depend on service (and service depends on repository)
fun Route.userRoutes(userRepository: UserRepository) {
    route("/users") {
        get {
            val users = userRepository.findAll()
            call.respond(users)
        }

        get("/{id}") {

            val id: Long = call.parameters["id"]?.toLongOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val user = userRepository.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(user)
        }

        post {
            val user = call.receive<User>()

            val createdUser = userRepository.create(user)
            call.respond(HttpStatusCode.Created, createdUser)
        }
    }
}
