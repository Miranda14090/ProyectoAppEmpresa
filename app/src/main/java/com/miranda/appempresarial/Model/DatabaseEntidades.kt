package com.miranda.appempresarial.Model

import com.miranda.appempresarial.api.Servicios.ServiciosDatabase

interface DatabaseEntidades {
    fun createDB(serviciosDatabase: ServiciosDatabase)
    fun spinnerEstados(serviciosDatabase: ServiciosDatabase)

    fun obtenerIdUser(serviciosDatabase: ServiciosDatabase):Int
    fun crearUsuario(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password:String)
    fun updateUserAcces(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password: String)
    fun obtenerUsuario(serviciosDatabase: ServiciosDatabase):UserFingerprintDB?
}