package resa.mario.entities

import org.ufoss.kotysa.h2.H2Table
import resa.mario.models.Empleado

object EmpleadosTable : H2Table<Empleado>("empleados") {
    val id = bigInt(Empleado::id).primaryKey()

    val name = varchar(Empleado::name, size = 50)
    val email = varchar(Empleado::email, size = 50)
    val departamentoId = bigInt(Empleado::departamentoId, "departamento_id").foreignKey(DepartamentosTable.id)
}