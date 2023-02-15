package resa.mario.repositories.usuario

import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.entities.UsuariosTable
import resa.mario.models.Usuario
import resa.mario.repositories.CrudRepository
import resa.mario.services.DataBaseService

private val log = KotlinLogging.logger {}

@Single
@Named("UsuariosRepository")
class UsuariosRepository(
    private val dataBaseService: DataBaseService
) : CrudRepository<Usuario, Long> {

    override suspend fun findAll(): Flow<Usuario> {
        log.info { "Mostrando a todos los usuarios" }

        return dataBaseService.client selectAllFrom UsuariosTable
    }

    override suspend fun delete(entity: Usuario): Usuario? {
        log.info { "Eliminando empleado con id: ${entity.id}" }

        entity.let {
            val res = (dataBaseService.client deleteFrom UsuariosTable where UsuariosTable.id eq it.id!!).execute()

            if (res > 0) {
                return entity
            } else return null
        }
    }

    override suspend fun update(id: Long, entity: Usuario): Usuario? {
        log.info { "Actualizando usuario con id: $id" }

        entity.let {
            val res = (dataBaseService.client update UsuariosTable
                    set UsuariosTable.username eq entity.username
                    set UsuariosTable.password eq entity.password
                    set UsuariosTable.role eq entity.role
                    where UsuariosTable.id eq id).execute()

            if (res > 0) {
                return entity
            } else return null
        }
    }

    private suspend fun findByUsername(username: String): Usuario? {
        log.info { "Buscando usuario con nombre: $username " }

        return (dataBaseService.client selectFrom UsuariosTable where UsuariosTable.username eq username).fetchFirstOrNull()
    }

    // -- IMPORTANTE; METODO "LOGIN" -> FindByName
    suspend fun login(username: String, password: String): Usuario? {
        log.info { "Inicio de sesion del usuario: $username" }

        val user = findByUsername(username) ?: return null

        user.let {
            if (Bcrypt.verify(password, user.password.encodeToByteArray())) {
                return user
            } else return null
        }
    }

    override suspend fun save(entity: Usuario): Usuario {
        log.info { "Almacenando usuario: ${entity.username}" }

        return dataBaseService.client insertAndReturn entity
    }

    override suspend fun findById(id: Long): Usuario? {
        log.info { "Buscando usuario con id: $id" }

        return (dataBaseService.client selectFrom UsuariosTable where UsuariosTable.id eq id).fetchFirstOrNull()
    }

}