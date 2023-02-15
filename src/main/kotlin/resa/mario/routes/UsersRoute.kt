package resa.mario.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject
import resa.mario.dto.UsuarioDTOLogin
import resa.mario.dto.UsuarioDTORegister
import resa.mario.dto.UsuarioDTOResponse
import resa.mario.mappers.toDTO
import resa.mario.mappers.toUsuario
import resa.mario.models.Usuario
import resa.mario.services.TokensService
import resa.mario.services.usuario.UsuarioServiceImpl

private const val END_POINT = "api/usuarios"

fun Application.usuariosRoutes() {
    // 1. Luego de definir el END_POINT, inyectamos tanto el servicio de usuarios como el de tokens
    val usuarioService: UsuarioServiceImpl by inject()
    val tokensService: TokensService by inject()

    routing {
        route("/$END_POINT") {

            // 2. Generamos la ruta para el registro
            post("/register") {
                try {
                    val dto = call.receive<UsuarioDTORegister>()
                    val user = usuarioService.save(dto.toUsuario())
                    call.respond(HttpStatusCode.Created, user.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // 3. Generamos la ruta para el login
            post("/login") {
                try {
                    val dto = call.receive<UsuarioDTOLogin>()
                    val user = usuarioService.login(dto.username, dto.password)

                    user?.let {
                        val token = tokensService.generateJWT(user)
                        call.respond(HttpStatusCode.OK, token)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // 4. Una vez comprobados los metodos anteriores, se comienza con los metodos que se encuentran protegidos
            // 5. Para enviar por Thunder Client [VSC Plugin] el token, se usa la pestaña @Auth -> Bearer
            authenticate {
                // Get /me
                get("/me") {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        // 5,5. Los claim vienen con comillas
                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(userId.toLong())

                        user.let {
                            call.respond(HttpStatusCode.OK, user.toDTO())
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                // Get users -> SOLO ADMIN
                get("/list") {
                    try {
                        val token = call.principal<JWTPrincipal>()

                        val userId = token?.payload?.getClaim("userId").toString().replace("\"", "")
                        val user = usuarioService.findById(userId.toLong())

                        user.let {
                            if (user.role == Usuario.Role.ADMIN.name) {
                                val res = mutableListOf<UsuarioDTOResponse>()
                                usuarioService.findAll().toList().forEach { res.add(it.toDTO()) }

                                call.respond(HttpStatusCode.OK, res)
                            } else {
                                call.respond(HttpStatusCode.Unauthorized, "Not Authorized")
                            }
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }
            }
        }
    }
}