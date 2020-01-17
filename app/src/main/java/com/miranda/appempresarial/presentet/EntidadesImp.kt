package com.miranda.appempresarial.presentet

import com.miranda.appempresarial.Model.DatabaseEntidades
import com.miranda.appempresarial.Model.DatabaseEntidadesImp
import com.miranda.appempresarial.Model.UserFingerprintDB
import com.miranda.appempresarial.api.Servicios.ServiciosDatabase
import com.miranda.appempresarial.view.DatabaseView

class EntidadesImp(var view: DatabaseView):Entidades {

    val datos: DatabaseEntidades =
        DatabaseEntidadesImp()

    override fun createDB(serviciosDatabase: ServiciosDatabase) {
        datos.createDB(serviciosDatabase)
    }

    override fun spinnerEstados(serviciosDatabase: ServiciosDatabase) {
        datos.spinnerEstados(serviciosDatabase)
    }

    override fun obtenerIdUser(serviciosDatabase: ServiciosDatabase): Int {
        val id = datos.obtenerIdUser(serviciosDatabase)
        return id
    }

    override fun crearUsuario(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password: String) {
        datos.crearUsuario(serviciosDatabase,numeroEmpleado,password)
    }

    override fun updateUserAcces(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password: String) {
        datos.updateUserAcces(serviciosDatabase,numeroEmpleado,password)
    }

    override fun obtenerUsuario(serviciosDatabase: ServiciosDatabase): UserFingerprintDB? {
        val usuario =datos.obtenerUsuario(serviciosDatabase)
        return usuario
    }
}