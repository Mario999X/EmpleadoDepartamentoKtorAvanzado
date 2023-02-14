package resa.mario.db

import resa.mario.models.Departamento
import resa.mario.models.Empleado

fun getDepartamentos() = listOf(
    Departamento(
        id = 1,
        nombre = "Interfaces",
        presupuesto = 500.0
    ),
    Departamento(
        id = 2,
        nombre = "Administracion",
        presupuesto = 700.0
    ),
    Departamento(
        id = 3,
        nombre = "PSP",
        presupuesto = 400.0
    )
)

fun getEmpleados() = listOf(
    Empleado(1, "Mario", "mario@gmail.com", getDepartamentos()[0].id),
    Empleado(2, "Alysys", "alysys@gmail.com", getDepartamentos()[1].id),
    Empleado(3, "Vincent", "vincent@gmail.com", getDepartamentos()[1].id),
    Empleado(4, "Kratos", "kratos@gmail.com"),
)