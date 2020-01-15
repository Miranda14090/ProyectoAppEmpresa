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
import kotlinx.android.synthetic.main.fragment_perfil_usuario.*


/**
 * A simple [Fragment] subclass.
 */
class PerfilUsuario : Fragment() {

    var firstLoging = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(firstLoging) {
            val empleado = InfoEmpleado(Consumo.TuNumeroDeEmpleado)
            val asistencias = ListaAsistencia(Consumo.TuNumeroDeEmpleado)
            Consumo.datosEmpleado(empleado, activity!!, "Datos", view)
            Consumo.listaAsistencia(asistencias, activity!!, "Asistencia", view)
            firstLoging = false
        }
        btnCerrarSesion.setOnClickListener {
            activity!!.finish()
        }
    }

    companion object {
        fun newInstance(): PerfilUsuario =
            PerfilUsuario()
    }


}
