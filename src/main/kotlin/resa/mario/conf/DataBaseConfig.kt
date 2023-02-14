package resa.mario.conf

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single
data class DataBaseConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val driver = config["driver"].toString()
    val protocol = config["protocol"].toString()
    val user = config["user"].toString()
    val password = config["password"].toString()
    val database = config["database"].toString()
    val initDatabaseData = config["initDatabaseData"]?.toBooleanStrictOrNull() ?: true

}