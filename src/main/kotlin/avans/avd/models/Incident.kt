package avans.avd.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Incident(
    // the user who reported this Incident
    val reportedBy: Long?,

    val category: String,
    val description: String,

    val latitude: Double,
    val longitude: Double,

    val priority: Priority = Priority.Low,
    val status: Status = Status.OPEN,
    val images: MutableList<String> = mutableListOf(),

    // metadata about creating, updating and completing the Incident report
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val updatedAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val completedAt: LocalDateTime? = null,

    val id: Long = 0
)

enum class Priority {
    Low, Medium, High, Vital
}

enum class Status { OPEN, ASSIGNED, DONE }
