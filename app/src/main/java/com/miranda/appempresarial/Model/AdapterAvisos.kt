package com.miranda.appempresarial.Model

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.ListaDeAvisos
import com.miranda.appempresarial.view.AvisosActivity

import kotlinx.android.synthetic.main.item_avisos.view.*
import kotlinx.android.synthetic.main.item_avisos.view.txtTitulo

class AdapterAvisos(var lista:ArrayList<ListaDeAvisos>):RecyclerView.Adapter<AdapterAvisos.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var vistaAvisos = LayoutInflater.from(parent.context).inflate(R.layout.item_avisos,parent,false)

        return MyViewHolder(vistaAvisos)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item=lista.get(position)
        holder.enlazarItem(item)
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        fun enlazarItem(
            listaDeAvisos: ListaDeAvisos
        ){

            itemView.txtTitulo.text=listaDeAvisos.titulo
            itemView.txtFecha.text=listaDeAvisos.emision


            if(listaDeAvisos.estatus == true){
                itemView.txtEstatus.visibility=View.INVISIBLE


            }else { itemView.txtEstatus.setImageResource(R.drawable.ic_aviso_red) }


            itemView.btnVer.setOnClickListener {

                itemView.txtEstatus.visibility=View.INVISIBLE
                val bundle = Bundle()
                bundle.putSerializable("Avisos", listaDeAvisos)

                val intent = Intent(itemView.context, AvisosActivity::class.java)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
                itemView.btnVer.isEnabled = false
            }

        }

    }

    fun opdateData(listaAvisos:ArrayList<ListaDeAvisos>){
        lista.clear()
        lista.addAll(listaAvisos)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        lista.removeAt(position)
        notifyItemRemoved(position)
    }


}

