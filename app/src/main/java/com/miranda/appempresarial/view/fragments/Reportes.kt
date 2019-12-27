package com.miranda.appempresarial.view.fragments


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import com.miranda.appempresarial.R
import kotlinx.android.synthetic.main.fragment_reportes.*

/**
 * A simple [Fragment] subclass.
 */
class Reportes : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reportes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        /*view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                when (v?.id) {
                    R.id.imgBack -> {/* do your code */
                    }
                    R.id.twoButton -> {/* do your code */
                    }
                    R.id.threeButton -> {/* do your code */
                    }
                    else -> {/* do your code */
                    }
                }
            }*/


            cardViewTecnico.setOnClickListener {
                    MostrarDescripcion()

                    tvTecnico.setTextColor(Color.GREEN)
                    tvMantenimiento.setTextColor(Color.GRAY)
                    tvAdministrativo.setTextColor(Color.GRAY)
                    tvServicio.setTextColor(Color.GRAY)

                }

                cardViewMantenimiento.setOnClickListener {
                    MostrarDescripcion()

                    tvTecnico.setTextColor(Color.GRAY)
                    tvMantenimiento.setTextColor(Color.GREEN)
                    tvAdministrativo.setTextColor(Color.GRAY)
                    tvServicio.setTextColor(Color.GRAY)
                }

                cardViewAdministrativo.setOnClickListener {
                    MostrarDescripcion()

                    tvTecnico.setTextColor(Color.GRAY)
                    tvMantenimiento.setTextColor(Color.GRAY)
                    tvAdministrativo.setTextColor(Color.GREEN)
                    tvServicio.setTextColor(Color.GRAY)
                }
                cardViewServicio.setOnClickListener {
                    MostrarDescripcion()

                    tvTecnico.setTextColor(Color.GRAY)
                    tvMantenimiento.setTextColor(Color.GRAY)
                    tvAdministrativo.setTextColor(Color.GRAY)
                    tvServicio.setTextColor(Color.GREEN)
                }


            descipcion.addTextChangedListener(
            object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    if (descipcion.length() >= 1) {
                        boton_EnviarReporte.visibility = View.VISIBLE
                    } else
                        boton_EnviarReporte.visibility = View.INVISIBLE
                }
            })




        boton_EnviarReporte.setOnClickListener{
            if(descipcion.text.toString().replace(" ","")=="" || descipcion.text.toString()[0]==' '){
                descipcion.error="Campo no valido"

            }else
                descipcion.error=null
        }


    }

    private fun MostrarDescripcion() {
        descipcion.visibility=View.VISIBLE
    }


    companion object {
        fun newInstance(): Reportes =
            Reportes()
    }



    /* fun onClick(v: View?) {
        when (v?.id) {
            R.id.cardViewTecnico -> {
                MostrarDescripcion()

                tvTecnico.setTextColor(Color.GREEN)
                tvMantenimiento.setTextColor(Color.GRAY)
                tvAdministrativo.setTextColor(Color.GRAY)
                tvServicio.setTextColor(Color.GRAY)
            }
            R.id.cardViewMantenimiento -> {
                MostrarDescripcion()

                tvTecnico.setTextColor(Color.GRAY)
                tvMantenimiento.setTextColor(Color.GREEN)
                tvAdministrativo.setTextColor(Color.GRAY)
                tvServicio.setTextColor(Color.GRAY)
            }
            else -> {
            }
        }



    }*/
}

