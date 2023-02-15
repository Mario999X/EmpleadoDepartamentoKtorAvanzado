package resa.mario

import io.ktor.server.application.*
import resa.mario.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    // Koin
    configureKoin()

    // Base de datos
    configureDataBase()

    configureSecurity()
    configureSerialization()
    configureRouting()

}

/**
 *  -- EXPLICACION --
 *
 *      -- USUARIOS Y Bcrypt --
 *
 *  1. Agregar los usuarios como cualquier otro dato
 *      - Modelo
 *      - Tabla
 *      - DatabaseService
 *      - Data
 *  2. Para la gestion de usuarios es muy conveniente el uso de dto, tales como:
 *      - UserRegisterDto
 *      - UserLoginDto
 *      - UserResponseDto
 *  3. Si se usan DTO, es tambien necesario el uso de mappers para pasar de modelo a dto y al reves
 *      - TIP: El cifrar la contraseña puede hacerse tanto en el servicio como en el mapper; en mi caso escogere este ultimo
 *      - Usare la libreria que estoy acostumbrado a manejar de Bcrypt, generare una clase objeto llamada Cifrador
 *
 *  4. Configuramos el repositorio de usuarios
 *  5. Configuramos el servicio
 *
 *      -- TOKEN Y SEGURIDAD --
 *
 *  6. Configuramos el plugin de seguridad
 *      6,1. Configuramos los parametros en el .conf
 *      6,2. Seguimos los pasos explicados en la clase [Security]
 *
 *  7. Configuramos las rutas
 *      - Seguir pasos de la clase [UsersRoute]
 *
 */
