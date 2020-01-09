package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class InfoEmpleadoResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("nombres") var nombres:String,
    @SerializedName("apellidoPaterno") var apellidoPaterno:String
) {
}

//0 Operacion exitosa
//-1 Error no controlado
// 2 Error en el formato de los datos
// 3 Datos incorrectos