package resa.mario.repositories.empleado

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.entities.EmpleadosTable
import resa.mario.models.Empleado
import resa.mario.repositories.CrudRepository
import resa.mario.services.DataBaseService

private val log = KotlinLogging.logger {}

@Single
@Named("EmpleadoRepository")
class EmpleadoRepository(
    private val dataBaseService: DataBaseService
) : CrudRepository<Empleado, Long> {

    override suspend fun findAll(): Flow<Empleado> {
        log.info { "Mostrando todos los empleados" }

        return dataBaseService.client selectAllFrom EmpleadosTable
    }

    override suspend fun delete(entity: Empleado): Empleado? {
        log.info { "Eliminando empleado con id: ${entity.id}" }

        entity.let {
            val res =
                (dataBaseService.client deleteFrom EmpleadosTable where EmpleadosTable.id eq it.id).execute()

            if (res > 0) {
                return entity
            } else return null
        }
    }

    override suspend fun update(id: Long, entity: Empleado): Empleado? {
        log.info { "Actualizando empleado con id: $id" }

        entity.let {
            val res = (dataBaseService.client update EmpleadosTable
                    set EmpleadosTable.name eq entity.name
                    set EmpleadosTable.email eq entity.email
                    set EmpleadosTable.departamentoId eq entity.departamentoId
                    where EmpleadosTable.id eq id).execute()

            if (res > 0) {
                return entity
            } else return null
        }
    }

    override suspend fun save(entity: Empleado): Empleado {
        log.info { "Almacenando empleado: $entity" }

        return dataBaseService.client insertAndReturn entity
    }

    override suspend fun findById(id: Long): Empleado? {
        log.info { "Buscando empleado con id: $id" }

        return (dataBaseService.client selectFrom EmpleadosTable where EmpleadosTable.id eq id).fetchFirstOrNull()

    }
}