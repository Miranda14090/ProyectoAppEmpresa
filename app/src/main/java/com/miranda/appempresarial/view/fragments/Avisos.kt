package com.miranda.appempresarial.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.RegistroAviso


import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Internet
import kotlinx.android.synthetic.main.fragment_avisos.*

/**
 * A simple [Fragment] subclass.
 */
class Avisos : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avisos, container, false)
    }

    companion object {
        fun newInstance(): Avisos = Avisos()
    }

    override fun onResume() {
        super.onResume()
        if((activity?.let { Internet.coprobarInternet(it) }!!)){
            val  datosEmpleado=RegistroAviso(Consumo.TuNumeroDeEmpleado)
            Consumo.mostrar_avisos(datosEmpleado,activity!!,"Avisos",recyclerNotificaciones)}
    }
}
