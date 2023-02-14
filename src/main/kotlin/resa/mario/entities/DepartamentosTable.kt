package resa.mario.entities
// https://ufoss.org/kotysa/table-mapping.html

import org.ufoss.kotysa.h2.H2Table
import resa.mario.models.Departamento

object DepartamentosTable : H2Table<Departamento>("departamentos") {
    val id = bigInt(Departamento::id).primaryKey()

    val nombre = varchar(Departamento::nombre, size = 50)
    val presupuesto = doublePrecision(Departamento::presupuesto)
}