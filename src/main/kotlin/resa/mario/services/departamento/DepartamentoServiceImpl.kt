package resa.mario.services.departamento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Departamento
import resa.mario.repositories.departamento.DepartamentoCachedRepository
import resa.mario.repositories.empleado.EmpleadoRepository

@Single
class DepartamentoServiceImpl(
    @Named("DepartamentoCachedRepository")
    private val repository: DepartamentoCachedRepository,
    @Named("EmpleadoRepository")
    private val empleadoRepository: EmpleadoRepository
) : DepartamentoService {

    override suspend fun findAll(): Flow<Departamento> {
        return repository.findAll()
    }

    override suspend fun findById(id: Long): Departamento {
        return repository.findById(id) ?: throw Exception("No existe ese departamento")
    }

    override suspend fun save(entity: Departamento): Departamento {
        return repository.save(entity)
    }

    override suspend fun update(id: Long, entity: Departamento): Departamento {
        val existe = repository.findById(id)

        existe?.let {
            return repository.update(id, entity)!!
        } ?: throw Exception("No se encontro ese departamento")
    }

    override suspend fun delete(entity: Departamento): Departamento {
        val existe = repository.findById(entity.id)

        //System.err.println(existe)
        existe?.let {
            val empleados = empleadoRepository.findAll().toList().filter { it.departamentoId == existe.id }
            val count = empleados.size

            //System.err.println(empleados.size)
            if (count == 0) {
                return repository.delete(existe)!!
            } else throw Exception("No fue posible eliminar el departamento | $count empleados")
        } ?: throw Exception("No se encontro ese departamento")
    }
}