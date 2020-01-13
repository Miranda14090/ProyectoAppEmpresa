package com.miranda.appempresarial.Model

import com.google.gson.annotations.SerializedName

data class CambiarEstadoAviso(
    @SerializedName("numeroDeEmpleado") var numeroDeEmpleado:String,
    @SerializedName("id") var id:String
)
