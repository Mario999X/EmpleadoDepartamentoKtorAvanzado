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

    //configureSecurity()
    configureSerialization()
    configureRouting()

}
