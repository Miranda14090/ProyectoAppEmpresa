package com.miranda.appempresarial.presentet

import com.miranda.appempresarial.Model.UserFingerprintDB
import com.miranda.appempresarial.api.Servicios.ServiciosDatabase

interface DatabasePresenter {
    fun createDB(serviciosDatabase: ServiciosDatabase)
    fun spinnerEstados(serviciosDatabase: ServiciosDatabase)

    fun obtenerIdUser(serviciosDatabase: ServiciosDatabase):Int
    fun crearUsuario(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password:String)
    fun updateUserAcces(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password: String)
    fun obtenerUsuario(serviciosDatabase: ServiciosDatabase): UserFingerprintDB?
}