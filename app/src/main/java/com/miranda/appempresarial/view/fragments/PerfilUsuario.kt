package com.miranda.appempresarial.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.InfoEmpleado
import com.miranda.appempresarial.Model.ListaAsistencia
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Internet
import kotlinx.android.synthetic.main.fragment_perfil_usuario.*


/**
 * A simple [Fragment] subclass.
 */
class PerfilUsuario : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if((activity?.let { Internet.coprobarInternet(it) }!!)) {

            val asistencias = ListaAsistencia(Consumo.TuNumeroDeEmpleado)
            Consumo.listaAsistencia(asistencias, activity!!, "Asistencia", view)

            if (Consumo.firstLoging) {
                Consumo.firstLoging = false
                val empleado = InfoEmpleado(Consumo.TuNumeroDeEmpleado)
                Consumo.datosEmpleado(empleado, activity!!, "Datos", view)
            }
        }

        txtDatosEmpleadoPerfil.text = Consumo.datosEmpleado

        btnCerrarSesion.setOnClickListener {
            activity!!.finish()
        }
    }

    companion object {
        fun newInstance(): PerfilUsuario =
            PerfilUsuario()
    }
}
