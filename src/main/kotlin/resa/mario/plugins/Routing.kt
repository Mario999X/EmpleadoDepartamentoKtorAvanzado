package resa.mario.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import resa.mario.routes.departamentosRoutes
import resa.mario.routes.empleadosRoutes

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    departamentosRoutes()
    empleadosRoutes()
}
