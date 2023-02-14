package resa.mario.models

import kotlinx.serialization.Serializable

@Serializable
data class Departamento(
    val id: Long,
    val nombre: String,
    val presupuesto: Double
) {
    override fun toString(): String {
        return "Departamento(id=$id, nombre='$nombre', presupuesto=$presupuesto)"
    }
}
