package com.miranda.appempresarial.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miranda.appempresarial.Model.AdapterAvisos
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.RegistroAviso


import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.FragmentoListener
import com.miranda.appempresarial.api.ListaDeAvisos
import kotlinx.android.synthetic.main.fragment_avisos.*
import kotlinx.android.synthetic.main.fragment_avisos.view.*

/**
 * A simple [Fragment] subclass.
 */
class Avisos : Fragment(), FragmentoListener {

    lateinit var datosEmpleado:RegistroAviso
    lateinit var mRecyclerV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avisos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datosEmpleado=RegistroAviso(Consumo.TuNumeroDeEmpleado)
        Consumo.mostrar_avisos(datosEmpleado,activity!!,"Avisos",view)
        mRecyclerV = view.recyclerNotificaciones
    }

    fun llenarRecycler(
        listaAvisos: ArrayList<ListaDeAvisos>,
        miRecycler: RecyclerView
    ){

        miRecycler.layoutManager= LinearLayoutManager(activity)
        val miAdaptador=
            AdapterAvisos(listaAvisos,this)
        miRecycler.adapter=miAdaptador
    }

    companion object {
        fun newInstance(): Avisos = Avisos()
    }



    override fun cambiarFragment() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
