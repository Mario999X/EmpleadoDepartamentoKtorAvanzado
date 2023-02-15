package resa.mario.services.usuario

import kotlinx.coroutines.flow.Flow
import resa.mario.models.Usuario

interface UsuarioService {

    suspend fun findAll(): Flow<Usuario>
    suspend fun findById(id: Long): Usuario
    suspend fun login(username: String, password: String): Usuario?
    suspend fun save(entity: Usuario): Usuario
    suspend fun update(id: Long, entity: Usuario): Usuario
    suspend fun delete(entity: Usuario): Usuario
}