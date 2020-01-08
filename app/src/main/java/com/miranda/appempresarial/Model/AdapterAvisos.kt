package com.miranda.appempresarial.Model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.FragmentoListener
import com.miranda.appempresarial.api.ListaDeAvisos
import kotlinx.android.synthetic.main.item_avisos.view.*

class AdapterAvisos(var lista:ArrayList<ListaDeAvisos>,var vista:FragmentoListener):RecyclerView.Adapter<AdapterAvisos.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var vistaAvisos = LayoutInflater.from(parent.context).inflate(R.layout.item_avisos,parent,false)

        return MyViewHolder(vistaAvisos,vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item=lista.get(position)
        holder.enlazarItem(item,vista)
    }

    class MyViewHolder(itemView: View, view: FragmentoListener):RecyclerView.ViewHolder(itemView) {
        fun enlazarItem(
            listaDeAvisos: ListaDeAvisos,
            vista:FragmentoListener
        ){

            itemView.txtTitulo.text=listaDeAvisos.titulo
            itemView.txtFecha.text=listaDeAvisos.emision

            Log.d("mensaje","${listaDeAvisos.estatus}")
            if(listaDeAvisos.estatus == true){
                itemView.txtEstatus.setImageResource(R.drawable.ic_aviso_verde)

                Log.d("mensaje","Estoy 6235")

            }else { itemView.txtEstatus.setImageResource(R.drawable.ic_aviso_red) }

            itemView.txtFecha.setOnClickListener {
                vista.cambiarFragment()
            }





        }

    }
}