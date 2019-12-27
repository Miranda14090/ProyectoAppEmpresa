package com.miranda.appempresarial.Model

import com.google.gson.annotations.SerializedName

data class LoginUser (
    @SerializedName("password") var password:String,
    @SerializedName("numeroDeEmpleado") var numero_de_empleado:String
){
}