package avans.avd.routes

import avans.avd.models.Incident
import avans.avd.models.IncidentRepository
import avans.avd.models.Status
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.incidentRoutes(incidentRepository: IncidentRepository) {
    route("/incidents") {

    }
}
