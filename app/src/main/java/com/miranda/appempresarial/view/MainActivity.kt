package com.miranda.appempresarial.view


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

class MainActivity : AppCompatActivity(),Reportes.ReportesListener{

        lateinit var toolbar: ActionBar
        lateinit var  miAdaptador:AdapterAvisos

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
                        if(!Consumo.asistenciaDelDia){
                            val fragment = asistencia.newInstance()
                            openFragment(fragment)
                            true
                        }else{
                            bottom_navigation.selectedItemId = R.id.Mi_Perfil
                            false
                        }
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

        override fun onBackPressed() {
        super.onBackPressed()
        when(bottom_navigation.selectedItemId){
            R.id.action_asistencia ->{
                bottom_navigation.selectedItemId = R.id.Mi_Perfil
            }

            R.id.Mi_Perfil -> {
                finish()
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
}
