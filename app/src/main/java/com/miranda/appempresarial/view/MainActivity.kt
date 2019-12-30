package com.miranda.appempresarial.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.miranda.appempresarial.R
import com.miranda.appempresarial.view.fragments.PerfilUsuario
import com.miranda.appempresarial.view.fragments.Reportes
import com.miranda.appempresarial.view.fragments.StatusReportFragment
import com.miranda.appempresarial.view.fragments.asistencia
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),Reportes.ReportesListener {
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

                R.id.action_noticias -> {
                    val fragment = PerfilUsuario.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.action_Rproblema -> {
                    val fragment = Reportes.newInstance()
                    openFragment(fragment)
                    true
                }

                    else -> false
            }
        }
        navigationBar.selectedItemId =
            R.id.action_asistencia
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


}
