package com.miranda.appempresarial.Model

import com.google.gson.annotations.SerializedName

data class RegistroAsistencia (
    @SerializedName("imagen") var imagen:String,
    @SerializedName("numeroDeEmpleado") var numeroDeEmpleado:String
) {
}