package com.miranda.appempresarial.view.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.InfoEmpleado
import com.miranda.appempresarial.Model.ListaAsistencia
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Internet
import com.miranda.appempresarial.view.InicioDeSesion
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
            mensaje(activity!!,getString(R.string.sesion), getString(R.string.cerrarSesionPregunta))
        }
    }

    companion object {
        fun newInstance(): PerfilUsuario =
            PerfilUsuario()
    }

    private fun mensaje(context: Context, titulo: String, s: String) {
        val dialogoRespuesta = AlertDialog.Builder(context)

        dialogoRespuesta.setTitle(titulo)
            .setMessage(s)
            .setPositiveButton(R.string.msnOk,
                DialogInterface.OnClickListener { _, _ ->
                    Consumo.datosEmpleado = ""
                    Consumo.asistenciaDelDia = false
                    Consumo.firstLoging = true
                    val pref = PreferenceManager.getDefaultSharedPreferences(activity!!)
                    pref.edit().clear().commit()
                    val intent = Intent(context, InicioDeSesion::class.java)
                    startActivity(intent)
                    activity!!.finish()
                }) //despues del lambda -> se pone la accion
            .setNegativeButton(R.string.btnCancelar,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
        dialogoRespuesta.create()
        dialogoRespuesta.show()

    }
}
