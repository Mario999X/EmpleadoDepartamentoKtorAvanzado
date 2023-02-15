package resa.mario.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDTOLogin(
    val username: String,
    val password: String
)

@Serializable
data class UsuarioDTORegister(
    val username: String,
    val password: String,
    val role: String,
)

@Serializable
data class UsuarioDTOResponse(
    val username: String,
    val role: String,
    val createdAt: String
)