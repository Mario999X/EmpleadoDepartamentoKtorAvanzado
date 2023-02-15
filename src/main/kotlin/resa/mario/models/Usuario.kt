package resa.mario.models

import java.time.LocalDateTime

data class Usuario(
    val id: Long? = null,
    val username: String,
    val password: String,
    var role: String = Role.USER.name,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    enum class Role {
        USER, ADMIN
    }
}
