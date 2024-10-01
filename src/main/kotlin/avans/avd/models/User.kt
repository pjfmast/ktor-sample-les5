package avans.avd.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val password: String,
    val email: String,
    val role: Role = Role.USER,
    val id: Long = 0,
)

enum class Role {
    USER,
    OFFICIAL,
    ADMIN
}