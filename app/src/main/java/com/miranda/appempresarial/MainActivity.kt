package com.miranda.appempresarial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_formulario.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.text.InputType
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import java.util.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_perfil_usuario.*

class MainActivity : AppCompatActivity() {
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
        navigationBar.selectedItemId = R.id.action_asistencia
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
