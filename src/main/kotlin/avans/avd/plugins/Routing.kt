package avans.avd.plugins

import avans.avd.models.FakeIncidentRepository
import avans.avd.models.FakeUserRepository
import avans.avd.routes.incidentRoutes
import avans.avd.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Welcome to the incident app!")
        }

        userRoutes(FakeUserRepository)
        incidentRoutes(FakeIncidentRepository)
    }
}



