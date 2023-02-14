package resa.mario.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import resa.mario.models.Departamento
import resa.mario.services.departamento.DepartamentoServiceImpl

private const val END_POINT = "api/departamentos"

fun Application.departamentosRoutes() {

    val departamentoServiceImpl: DepartamentoServiceImpl by inject()

    routing {
        route("/$END_POINT") {
            // Get All
            get {
                val result = mutableListOf<Departamento>()

                // Procesamos el flujo
                departamentoServiceImpl.findAll().collect {
                    result.add(it)
                }
                println(Json.encodeToString<List<Departamento>>(result))

                call.respond(HttpStatusCode.OK, result)
            }

            // Get by Id /endpoint/id
            get("{id}") {
                try {
                    val id = call.parameters["id"]!!.toLong()
                    val departamento = departamentoServiceImpl.findById(id)

                    call.respond(HttpStatusCode.OK, departamento.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Post /endpoint
            /*
             * EN THUNDERCLIENT, HEADERS -> Content-Type __ application/json
             * Luego en BODY -> JSON
            */
            post {
                try {
                    val departamentoReceive = call.receive<Departamento>()
                    val departamentoSave = departamentoServiceImpl.save(departamentoReceive)
                    call.respond(
                        HttpStatusCode.Created, departamentoServiceImpl.findById(departamentoSave.id).toString()
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}") {
                try {
                    val id = call.parameters["id"]?.toLong()!!
                    val request = call.receive<Departamento>()
                    val departamento = departamentoServiceImpl.update(id, request)
                    call.respond(HttpStatusCode.OK, departamento.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}") {
                try {
                    val id = call.parameters["id"]?.toLong()!!

                    val departamentoDelete = departamentoServiceImpl.findById(id)
                    departamentoServiceImpl.delete(departamentoDelete)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }
}