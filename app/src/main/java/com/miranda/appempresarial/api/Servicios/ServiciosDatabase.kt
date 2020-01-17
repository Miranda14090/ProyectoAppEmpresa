package com.miranda.appempresarial.api.Servicios

import com.miranda.appempresarial.Model.EntidadesFederativa
import com.miranda.appempresarial.Model.UserFingerprintDB
import io.realm.Realm

class ServiciosDatabase(val realm:Realm) {

    fun obtenerEntidades():List<EntidadesFederativa>{
        var entidad = realm.where(EntidadesFederativa::class.java).findAll()
        return entidad
    }
    fun obtenerUltimoId():Int{
        val id = realm.where(EntidadesFederativa::class.java).max("id")
        val ultimoId = if(id == null)1 else (id.toInt()+1)
        return ultimoId
    }
    fun crearEntidad(id:Int,entidad:String){
        realm.beginTransaction()
        val estado:EntidadesFederativa = realm.createObject(EntidadesFederativa::class.java,id)
        estado.entidad = entidad
        realm.commitTransaction()
    }
    fun obtenerEntidadPorId(id:Int):EntidadesFederativa?{
        var entidad = realm.where(EntidadesFederativa::class.java).equalTo("id",id).findFirst()
        return entidad
    }
    fun eliminarEntidad(id:Int){
        val entidad = obtenerEntidadPorId(id)
        if(entidad!=null){
            realm.beginTransaction()
            entidad.deleteFromRealm()
            realm.commitTransaction()
        }
    }

    fun obtenerUltimoIdUser():Int{
        val id = realm.where(UserFingerprintDB::class.java).max("id")
        val ultimoId = if(id == null)0 else (id.toInt()+1)
        return ultimoId
    }
    fun crearUsuario(id:Int,numeroEmpleado:String, password:String){
        realm.beginTransaction()
        val usuario: UserFingerprintDB = realm.createObject(UserFingerprintDB::class.java,id)
        usuario.numeroEmpledo = numeroEmpleado
        usuario.password = password
        realm.commitTransaction()
    }
    fun updateUser(numeroEmpleado:String, password:String){
        val id = 1
        val usuario = realm.where(UserFingerprintDB::class.java).equalTo("id",id).findFirst()
        realm.beginTransaction()
        usuario!!.numeroEmpledo = numeroEmpleado
        usuario!!.password = password
    }
    fun obtenerUsuario(): UserFingerprintDB?{
        val id = 1
        val usuario = realm.where(UserFingerprintDB::class.java).equalTo("id",id).findFirst()
        return usuario
    }
}