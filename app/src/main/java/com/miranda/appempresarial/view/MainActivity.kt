package com.miranda.appempresarial.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.miranda.appempresarial.Model.AdapterAvisos
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.InfoEmpleado
import com.miranda.appempresarial.Model.ListaAsistencia
import com.miranda.appempresarial.view.fragments.CuerpoAviso
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.FragmentoListener
import com.miranda.appempresarial.api.ListaDeAvisos
import com.miranda.appempresarial.api.ValidarAsistenciaResponse
import com.miranda.appempresarial.view.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),Reportes.ReportesListener, FragmentoListener {

    lateinit var toolbar: ActionBar

    override fun cambiarFragment() {
        /*supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                CuerpoAviso(), ""
            )
            .commit()*/
        openFragment(CuerpoAviso())
    }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            toolbar = supportActionBar!!
            val asistencia = ListaAsistencia(Consumo.TuNumeroDeEmpleado)
            Consumo.validarAsistencia(asistencia,applicationContext,"Asistencia")
            setupNavigation(bottom_navigation)
        }

        fun setupNavigation(navigationBar: BottomNavigationView) {
            navigationBar.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_asistencia -> {
                        val fragment = asistencia.newInstance()
                        openFragment(fragment)
                        true
                    }

                    R.id.Mi_Perfil -> {
                        val fragment = PerfilUsuario.newInstance()
                        openFragment(fragment)
                        true
                    }
                    R.id.action_Rproblema -> {
                        val fragment = Reportes.newInstance()
                        openFragment(fragment)
                        true
                    }
                    R.id.action_notificacion -> {
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
        val miAdaptador=
            AdapterAvisos(listaAvisos,this)
        miRecycler.adapter=miAdaptador
    }

}
