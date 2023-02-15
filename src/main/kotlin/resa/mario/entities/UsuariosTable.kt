package resa.mario.entities

import org.ufoss.kotysa.h2.H2Table
import resa.mario.models.Usuario

object UsuariosTable : H2Table<Usuario>("users") {
    val id = autoIncrementBigInt(Usuario::id).primaryKey()

    val username = varchar(Usuario::username, size = 20)
    val password = varchar(Usuario::password, size = 255)
    val role = varchar(Usuario::role, size = 10)
    val createdAt = timestamp(Usuario::createdAt, "created_at")
}