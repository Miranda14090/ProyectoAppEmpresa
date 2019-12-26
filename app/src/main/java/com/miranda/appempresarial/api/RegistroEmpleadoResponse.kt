package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class RegistroEmpleadoResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("numeroDeEmpleado") var numeroDeEmpleado:String
) {
}