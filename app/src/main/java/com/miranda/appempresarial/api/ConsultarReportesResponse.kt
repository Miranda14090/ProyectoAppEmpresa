package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class ConsultarReportesResponse(
    @SerializedName("codigoOperacion") var codigoDeOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("reportes") var reportes:List<ListaDeReporte>
) {
}

class ListaDeReporte(
    @SerializedName("folio") var folio:String,
    @SerializedName("cuerpo") var cuerpo:String,
    @SerializedName("clasificacion") var clasificacion:String,
    @SerializedName("estatus") var estatus:String

) {
}
