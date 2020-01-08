package com.miranda.appempresarial.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.miranda.appempresarial.R
import com.miranda.appempresarial.view.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),Reportes.ReportesListener,PermissionsView {
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = supportActionBar!!
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
                }R.id.action_notificacion -> {
                val fragment = notificaciones.newInstance()
                openFragment(fragment)
                true
            }

                    else -> false
            }
        }
        navigationBar.selectedItemId =
            R.id.Mi_Perfil
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun reporteFinishCallback(){
        val fragment = StatusReportFragment.newInstance()
        openFragment(fragment)
    }

    companion object {
        fun newInstance(): MainActivity =
            MainActivity()
    }


}
