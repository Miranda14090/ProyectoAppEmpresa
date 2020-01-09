package com.miranda.appempresarial.Model


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.Asistencia
import kotlinx.android.synthetic.main.item_lista_asistencia.view.*

class AdapterAsistencia(var lista:ArrayList<Asistencia>): RecyclerView.Adapter<AdapterAsistencia.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_asistencia,parent,false)
        return MyViewHolder(item)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item =lista.get(position)
        holder.enlazarVista(item)
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun enlazarVista(asistenciaLista: Asistencia) {
            itemView.txtFechaAsistencia.text = asistenciaLista.fecha
        }

    }


}