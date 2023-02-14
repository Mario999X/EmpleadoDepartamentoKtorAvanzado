package resa.mario.repositories.departamento

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.entities.DepartamentosTable
import resa.mario.models.Departamento
import resa.mario.repositories.CrudRepository
import resa.mario.services.DataBaseService

private val log = KotlinLogging.logger {}

@Single
@Named("DepartamentoRepository")
class DepartamentoRepository(
    private val dataBaseService: DataBaseService
) : CrudRepository<Departamento, Long> {

    override suspend fun findAll(): Flow<Departamento> {
        log.info { "Mostrando todos los departamentos" }
        return dataBaseService.client selectAllFrom DepartamentosTable
    }

    override suspend fun delete(entity: Departamento): Departamento? {
        log.info { "Eliminando departamento con id: ${entity.id}" }

        entity.let {
            val res =
                (dataBaseService.client deleteFrom DepartamentosTable where DepartamentosTable.id eq it.id).execute()

            if (res > 0) {
                return entity
            } else return null
        }
    }

    override suspend fun update(id: Long, entity: Departamento): Departamento? {
        log.info { "Actualizando departamento con id: $id" }

        entity.let {
            val res = (dataBaseService.client update DepartamentosTable
                    set DepartamentosTable.nombre eq entity.nombre
                    set DepartamentosTable.presupuesto eq entity.presupuesto
                    where DepartamentosTable.id eq id).execute()

            if (res > 0) {
                return entity
            } else return null
        }
    }

    override suspend fun save(entity: Departamento): Departamento {
        log.info { "Almacenando departamento: $entity" }

        return dataBaseService.client insertAndReturn entity
    }

    override suspend fun findById(id: Long): Departamento? {
        log.info { "Buscando departamento con id: $id" }

        return (dataBaseService.client selectFrom DepartamentosTable where DepartamentosTable.id eq id).fetchFirstOrNull()
    }
}