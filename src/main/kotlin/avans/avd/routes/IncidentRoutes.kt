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
        get {
            val incidents = incidentRepository.findAll()
            call.respond(HttpStatusCode.OK, incidents)
        }

        get("/{id}") {
            val id: Long = call.parameters["id"]?.toLongOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest)
            val incident: Incident = incidentRepository.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, incident)
        }

        post {
            val incident: Incident = call.receive<Incident>()

            val createdIncident = incidentRepository.create(incident)
            call.respond(HttpStatusCode.Created, createdIncident)
        }

        put("/{id}") {
            val id: Long = call.parameters["id"]?.toLongOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest)

            incidentRepository.findById(id)
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            val incident = call.receive<Incident>()

            if (incident.id != id) return@put call.respond(HttpStatusCode.BadRequest)

            incidentRepository.update(incident)

            call.respond(HttpStatusCode.Accepted) // or HttpStatusCode.OK
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest)
            incidentRepository.delete(id)

            call.respond(HttpStatusCode.OK)
        }

        // The data for patch can better be put in the body of a request.
        // Here queryParameters are used, to demonstrate this
        patch("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@patch call.respond(HttpStatusCode.BadRequest)

            val incident = incidentRepository.findById(id)
                ?: return@patch call.respond(HttpStatusCode.NotFound)
            if (incident.id != id) return@patch call.respond(HttpStatusCode.BadRequest)

            call.queryParameters["status"]?.let { status ->
                if (status in Status.entries.map { it.name }) {
                    val newStatus = Status.valueOf(status)
                    incidentRepository.changeStatus(incident, newStatus)
                    return@patch call.respond(HttpStatusCode.OK)
                } else return@patch call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
