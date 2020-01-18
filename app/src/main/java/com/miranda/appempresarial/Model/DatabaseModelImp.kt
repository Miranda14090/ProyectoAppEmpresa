package com.miranda.appempresarial.Model


import com.miranda.appempresarial.api.Servicios.ServiciosDatabase


class DatabaseModelImp: DatabaseModel {

    val entidases = arrayOf("Entidad Federativa","Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "CDMX",
        "Chiapas", "Chihuahua", "Coahuila", "Colima", "Durango", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "México",
        "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí",
        "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas")

    lateinit var entidadesList: EntidadesFederativa

    override fun createDB(serviciosDatabase: ServiciosDatabase)
    {
        val serviEnt = serviciosDatabase
        for (i in entidases.indices)
        {
            var ultimoId = serviEnt.obtenerUltimoId()
            serviEnt.crearEntidad(ultimoId,this.entidases[i])
        }
       /* val entidadesList = serviEnt.obtenerEntidades()
        entidadesList.forEach {
            Log.d("DB", "id de entidad: ${it.id}  Entidad: ${it.entidad}\n")
        }*/
    }

    override fun spinnerEstados(serviciosDatabase: ServiciosDatabase) {
        val serviEnt = serviciosDatabase
        for (i in 1..33){
            entidadesList = serviEnt.obtenerEntidadPorId(i)!!
            Consumo.entidades[i-1] = entidadesList.entidad
        }
    }

    override fun obtenerIdUser(serviciosDatabase: ServiciosDatabase): Int {
        val serviUser = serviciosDatabase
        val id = serviUser.obtenerUltimoIdUser()
        return id
    }

    override fun crearUsuario(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password:String) {
        val serviUser = serviciosDatabase
        serviUser.crearUsuario(1, numeroEmpleado, password)
    }

    override fun updateUserAcces(serviciosDatabase: ServiciosDatabase, numeroEmpleado: String, password: String) {
        val serviUser = serviciosDatabase
        serviUser.updateUser(numeroEmpleado,password)
    }

    override fun obtenerUsuario(serviciosDatabase: ServiciosDatabase): UserFingerprintDB? {
        val serviUser = serviciosDatabase
        val usuario = serviUser.obtenerUsuario()
        return usuario
    }
}