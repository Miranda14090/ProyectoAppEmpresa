package com.miranda.appempresarial.Model

import com.google.gson.annotations.SerializedName

data class Empleados (
    @SerializedName("nombres") var nombres:String,
    @SerializedName("apellidoPaterno") var apellidoPaterno:String,
    @SerializedName("apellidoMaterno") var apellidoMaterno:String,
    @SerializedName("edad") var edad:Int,
    @SerializedName("fechaDeNacimiento") var fechaDeNacimiento:String,
    @SerializedName("entidadFederativa") var varentidadFederativa:String,
    @SerializedName("password") var password:String
){
}