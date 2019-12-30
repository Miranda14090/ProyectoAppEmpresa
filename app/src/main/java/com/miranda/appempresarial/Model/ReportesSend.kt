package com.miranda.appempresarial.Model

import com.google.gson.annotations.SerializedName

data class ReportesSend(
    @SerializedName("cuerpo") var cuerpo:String,
    @SerializedName("clasificacion") var calsificacion:Int,
    @SerializedName("numeroDeEmpleado") var numeroEmpleado:String
)  {
}