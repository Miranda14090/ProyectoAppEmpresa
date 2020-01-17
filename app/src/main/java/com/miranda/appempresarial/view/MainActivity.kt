package com.miranda.appempresarial.view


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.miranda.appempresarial.Model.*
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.ListaDeAvisos
import com.miranda.appempresarial.view.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),Reportes.ReportesListener,StatusReportFragment.StatusListener{

        lateinit var toolbar: ActionBar
        lateinit var  miAdaptador:AdapterAvisos

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            toolbar = supportActionBar!!
            val asistencia = ListaAsistencia(Consumo.TuNumeroDeEmpleado)
            Consumo.validarAsistencia(asistencia,applicationContext,"Asistencia")
            setupNavigation(bottom_navigation)
            Consumo.TuPassword = ""
        }

        fun setupNavigation(navigationBar: BottomNavigationView) {
            navigationBar.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_asistencia -> {
                        if(!Consumo.asistenciaDelDia){
                            title="Asistencia"
                            val fragment = asistencia.newInstance()
                            openFragment(fragment)
                            true
                        }else{
                            mensajeAs(this,"Asistencia", "Ya registraste la asistencia hoy")
                            bottom_navigation.selectedItemId = R.id.Mi_Perfil
                            false

                        }
                    }
                    R.id.Mi_Perfil -> {
                        title="Inicio"
                        val fragment = PerfilUsuario.newInstance()
                        openFragment(fragment)
                        true
                    }
                    R.id.action_Rproblema -> {
                        title="Reportes"
                        val fragment = Reportes.newInstance()
                        openFragment(fragment)
                        true
                    }
                    R.id.action_notificacion -> {
                        title="Avisos"
                        val fragment = Avisos.newInstance()
                        openFragment(fragment)
                        true
                    }
                    else -> false
                }
            }
            navigationBar.selectedItemId =
                R.id.Mi_Perfil
        }

        fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

        override fun onBackPressed() {
        super.onBackPressed()
        when(bottom_navigation.selectedItemId){
            R.id.action_asistencia ->{
                bottom_navigation.selectedItemId = R.id.Mi_Perfil
            }

            R.id.Mi_Perfil -> {
                mensaje(this,getString(R.string.sesion), getString(R.string.cerrarSesionPregunta))
                 bottom_navigation.selectedItemId = R.id.Mi_Perfil
            }
            R.id.action_Rproblema -> {
                if(!Consumo.focusReportsView){
                    bottom_navigation.selectedItemId = R.id.action_notificacion}
            }
            R.id.action_notificacion -> {
                bottom_navigation.selectedItemId = R.id.action_asistencia
            }
        }
        Consumo.focusReportsView = false
    }

        override fun reporteFinishCallback() {
            val fragment = StatusReportFragment.newInstance()
            openFragment(fragment)
        }

        companion object {
        fun newInstance(): MainActivity = MainActivity()
    }

        fun llenarRecycler(
            listaAvisos: ArrayList<ListaDeAvisos>,
            miRecycler: RecyclerView
        ){
        miRecycler.layoutManager=LinearLayoutManager(this)
         miAdaptador= AdapterAvisos(listaAvisos)
        miRecycler.adapter=miAdaptador

    }

    private fun mensaje(c: Context, titulo: String, s: String) {
        val dialogoRespuesta = AlertDialog.Builder(c)

        dialogoRespuesta.setTitle(titulo)
            .setMessage(s)
            .setPositiveButton(R.string.msnOk,
                DialogInterface.OnClickListener { _, _ ->
                    Consumo.datosEmpleado = ""
                    Consumo.asistenciaDelDia = false
                    Consumo.firstLoging = true
                    val intent = Intent(applicationContext, InicioDeSesion::class.java)
                    startActivity(intent)
                    finish()
                }) //despues del lambda -> se pone la accion
            .setNegativeButton(R.string.btnCancelar,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
        dialogoRespuesta.create()
        dialogoRespuesta.show()
    }

    private fun mensajeAs(c: Context, titulo: String, s: String) {
        val dialogoRespuesta = AlertDialog.Builder(c)

        dialogoRespuesta.setTitle(titulo)
            .setMessage(s)
            .setPositiveButton(R.string.msnOk,
                DialogInterface.OnClickListener { _, _ ->
                }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()
    }


    override fun statusFinishCallback() {
        val fragment = Reportes.newInstance()
        openFragment(fragment)
    }
}
