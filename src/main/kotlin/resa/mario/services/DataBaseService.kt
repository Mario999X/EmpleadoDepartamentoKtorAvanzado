package resa.mario.services

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Single
import org.ufoss.kotysa.H2Tables
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.tables
import resa.mario.conf.DataBaseConfig
import resa.mario.db.getDepartamentos
import resa.mario.db.getEmpleados
import resa.mario.entities.DepartamentosTable
import resa.mario.entities.EmpleadosTable

@Single
class DataBaseService(
    private val dataBaseConfig: DataBaseConfig
) {

    private val connectionOptions = ConnectionFactoryOptions.builder()
        .option(ConnectionFactoryOptions.DRIVER, dataBaseConfig.driver)
        .option(ConnectionFactoryOptions.PROTOCOL, dataBaseConfig.protocol)  // file, mem
        .option(ConnectionFactoryOptions.USER, dataBaseConfig.user)
        .option(ConnectionFactoryOptions.PASSWORD, dataBaseConfig.password)
        .option(ConnectionFactoryOptions.DATABASE, dataBaseConfig.database)
        .build()

    val client = ConnectionFactories
        .get(connectionOptions)
        .sqlClient(getTables())

    private val initData get() = dataBaseConfig.initDatabaseData

    private fun getTables(): H2Tables {
        return tables().h2(
            DepartamentosTable,
            EmpleadosTable
        )
    }

    private fun createTables() = runBlocking {
        launch {
            client createTableIfNotExists (DepartamentosTable)
            client createTableIfNotExists (EmpleadosTable)
        }
    }

    private fun clearData() = runBlocking {
        try {
            client deleteAllFrom EmpleadosTable
            client deleteAllFrom DepartamentosTable
        } catch (_: Exception) {
        }
    }

    private fun initDataTest() = runBlocking {
        // IMPORTANTE SEGUIR EL ORDEN DE LA GENERACION DE TABLAS

        getDepartamentos().forEach {
            client insert it
        }

        getEmpleados().forEach {
            client insert it
        }

    }

    fun initDataBaseService() {
        // creamos las tablas
        createTables()

        // Inicializamos los datos de la base de datos
        if (initData) {
            clearData()
            initDataTest()
        }
    }
}