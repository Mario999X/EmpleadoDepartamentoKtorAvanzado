package resa.mario.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import resa.mario.conf.TokenConfig
import resa.mario.services.TokensService

fun Application.configureSecurity() {

    // 2. Cargamos los parametros del conf
    val tokenConfigParam = mapOf<String, String>(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "realm" to environment.config.property("jwt.realm").getString()
    )

    // 3. Inyectamos la configuracion de Tokens
    val tokenConfig: TokenConfig = get { parametersOf(tokenConfigParam) }

    // 4. Inyectamos el servicio de tokens
    val jwtService: TokensService by inject()

    // 1. Esto en un primer momento se deja por defecto
    // 5. Eliminamos lo que haya dentro de todos los campos. Dejando unicamamente jwt {} mas el de authentication
    authentication {
        jwt {
            // 6. Cargamos el verificador
            verifier(jwtService.verifyJWT())
            // 7. Con realm aseguramos la ruta que estamos protegiendo
            realm = tokenConfig.realm
            // 8. Validamos el token de la forma que veamos conveniente
            validate {
                if (it.payload.audience.contains(tokenConfig.audience) &&
                    it.payload.getClaim("username").asString().isNotEmpty()
                ) {
                    JWTPrincipal(it.payload)
                } else null
            }

            // 9. En caso de token caducado o invalido
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Not authorized or expired")
            }
        }
    }
}
