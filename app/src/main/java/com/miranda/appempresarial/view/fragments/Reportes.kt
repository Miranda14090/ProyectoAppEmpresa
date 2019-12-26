package com.miranda.appempresarial.view.fragments


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


        cardViewTecnico.setOnClickListener {
            MostrarDescripcion()
        }

        cardViewMantenimiento.setOnClickListener {
            MostrarDescripcion()
        }

        cardViewAdministrativo.setOnClickListener {
            MostrarDescripcion()
        }
        cardViewServicio.setOnClickListener {
            MostrarDescripcion()
        }

        descipcion.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(descipcion.length()>=1){
                    boton_EnviarReporte.visibility=View.VISIBLE
                }else
                    boton_EnviarReporte.visibility=View.INVISIBLE
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

}

