package com.miranda.appempresarial.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.ListaDeReporte
import kotlinx.android.synthetic.main.item_list.view.*

class AdaptaterReports(var lista:ArrayList<ListaDeReporte>):RecyclerView.Adapter<AdaptaterReports.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var vista = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return MyViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item=lista.get(position)
        holder.enlazarItem(item)
    }

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        fun enlazarItem(listaDeReporte: ListaDeReporte){
            itemView.tvFolio.text=listaDeReporte.folio
            itemView.tvCuerpo.text=listaDeReporte.cuerpo
            itemView.tvClasificacion.text=listaDeReporte.clasificacion
            itemView.tvEstatus.text=listaDeReporte.estatus

        }

    }
}


