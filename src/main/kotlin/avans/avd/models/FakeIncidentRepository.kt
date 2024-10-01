package avans.avd.models

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object FakeIncidentRepository : IncidentRepository {
    private var currentId: Long = 0L
    private var imageId: Long = 0L
    private val incidents = mutableListOf<Incident>()

    // Seed the fake IncidentRepository with some dummy data
    // here runBlocking is used to bridge non-coroutine code with suspending functions, use it carefully: https://medium.com/@shivayogih25/unveiling-the-power-of-runblocking-in-kotlin-coroutines-48c3d15f78a3
    // When created an incident gets a unique id
    init {
        runBlocking {
            create(
                Incident(
                    3, /*Anne*/
                    "Traffic",
                    "Sink hole here. Dangerous situation! Quick fix needed.",
                    51.58677130730741,
                    4.808487370673,
                    Priority.High,
                    Status.ASSIGNED,
                )
            )
            create(
                Incident(
                    2 /*Henk*/,
                    "Street lights",
                    "At this corner 2 lights are broken",
                    51.59051650746655,
                    4.812002566502519,
                    Priority.Medium
                )
            )
            create(
                Incident(
                    2 /*Henk*/,
                    "neighbourhood",
                    "noise disturbance from illegal party",
                    51.58218477578439,
                    4.835727885428926,
                    Priority.Medium
                )
            )
            create(
                Incident(
                    3 /*Anne*/,
                    "Garbage dump",
                    "Some xtc lab dumped chemicals. ",
                    51.58907773104348,
                    4.80552621192238,
                    Priority.High
                )
            )
        }
    }

    override suspend fun findIncidentsForUser(userID: Long): List<Incident> =
        incidents.filter { it.reportedBy == userID }


    override suspend fun findAll(): List<Incident> = incidents.toList()

    override suspend fun findById(id: Long): Incident? = incidents.find { it.id == id }

    override suspend fun changeStatus(incident: Incident, status: Status) {
        val changedIncident = if (status == Status.DONE) {
            incident.copy(
                status = status,
                completedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        } else incident.copy(
            status = status,
        )

        update(changedIncident)
    }

    override suspend fun addImage(id: Long, imageFileName: String) {
        val incident = findById(id)
        if (incident != null) {
            imageId++

            incident.images.add(imageFileName)
        }
    }


    override suspend fun create(entity: Incident): Incident {
        currentId++
        val newIncident = entity.copy(
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            id = currentId
        )
        incidents.add(newIncident)
        return newIncident
    }

    override suspend fun update(entity: Incident) {
        require(incidents.any { it.id == entity.id }) { "not an update: ${entity.id} does not exist" }

        incidents.removeIf { it.id == entity.id }
        val changedIncident =
            entity.copy(updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
        incidents.add(changedIncident)
    }

    override suspend fun delete(id: Long): Boolean = incidents.removeIf { it.id == id }

}