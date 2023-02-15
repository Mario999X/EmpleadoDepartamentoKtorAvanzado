package resa.mario.mappers

import resa.mario.dto.UsuarioDTORegister
import resa.mario.dto.UsuarioDTOResponse
import resa.mario.models.Usuario
import resa.mario.services.Cifrador

fun Usuario.toDTO(): UsuarioDTOResponse {
    return UsuarioDTOResponse(
        username = username,
        role = role,
        createdAt = createdAt.toString()
    )
}

fun UsuarioDTORegister.toUsuario(): Usuario {
    return Usuario(
        username = username,
        password = Cifrador.cipher(password),
        role = role
    )
}