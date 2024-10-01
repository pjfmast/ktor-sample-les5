package avans.avd.models

interface IncidentRepository: CrudRepository<Incident, Long> {
    suspend fun findIncidentsForUser(userID: Long): List<Incident>
    suspend fun changeStatus(incident: Incident, status: Status)
    suspend fun addImage(id: Long, imageFileName: String)
}