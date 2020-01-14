package com.miranda.appempresarial.view.fragments


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.ReportesSend
import com.miranda.appempresarial.R
import kotlinx.android.synthetic.main.fragment_reportes.*
import com.miranda.appempresarial.view.fragments.Reportes as Reportes

/**
 * A simple [Fragment] subclass.
 */
class Reportes : Fragment() {
    var mCallBack:ReportesListener?=null
    var clasificacion:Int = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mCallBack=activity as ReportesListener?
        }catch (e :Exception){}
    }


    interface ReportesListener {
        fun reporteFinishCallback()
        fun ocuktarBarra()
        fun mostrarBarra()
    }

    companion object {
        fun newInstance(): Reportes =
            Reportes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reportes, container, false)
    }

    override fun onResume() {
        super.onResume()
        boton_EnviarReporte.visibility=View.INVISIBLE
        edtDescipcionRepFrac.setText("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        clasificacion = 0

        cardViewTecnico.setOnClickListener {
            clasificacion = 1
            MostrarDescripcion()
            MostrarColor()
        }

        cardViewMantenimiento.setOnClickListener {
            clasificacion = 2
            MostrarDescripcion()
            MostrarColor()
        }

        cardViewAdministrativo.setOnClickListener {
            clasificacion = 3
            MostrarDescripcion()
            MostrarColor()
        }
        cardViewServicio.setOnClickListener {
            clasificacion = 4
            MostrarDescripcion()
            MostrarColor()
        }

       /* edtDescipcionRepFrac.setOnClickListener {
            mCallBack?.ocuktarBarra()
        }*/


        edtDescipcionRepFrac.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if(edtDescipcionRepFrac.length()>=1){
                    boton_EnviarReporte.visibility=View.VISIBLE
                }else
                    boton_EnviarReporte.visibility=View.INVISIBLE
            }
        })

        boton_EnviarReporte.setOnClickListener{
            if(edtDescipcionRepFrac.text.toString().replace(" ","")=="" || edtDescipcionRepFrac.text.toString()[0]==' '){
                edtDescipcionRepFrac.error="Campo no valido"

            }
            else if(edtDescipcionRepFrac.text.toString()[(edtDescipcionRepFrac.length())-1]==' '){
                edtDescipcionRepFrac.error="Campo no valido"
            }
            else{
                edtDescipcionRepFrac.error=null
                val numeroDeEmpleado = Consumo.TuNumeroDeEmpleado

                val reporte = ReportesSend(edtDescipcionRepFrac.text.toString(),clasificacion,numeroDeEmpleado)
                Consumo.registrar_reporte(activity!!,reporte)
                edtDescipcionRepFrac.setText("")
               // mCallBack?.mostrarBarra()

            }
                
        }


        btnListaReportes.setOnClickListener {
            if(mCallBack!= null){
                mCallBack!!.reporteFinishCallback()
            }

        }

    }


    private fun MostrarColor(){
       when(clasificacion){
           1 -> {

              // tvTecnico.setTextColor(R.color.primaryColor.toInt())
               //tvTecnico.setTextColor(Color.parseColor("#558b2f"))
               tvTecnico.setTextColor(Color.GREEN)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GRAY)
           }
           2 -> {
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GREEN)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GRAY)
           }
           3 -> {
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GREEN)
               tvServicio.setTextColor(Color.GRAY)
           }
           4 -> {
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GREEN)
           }
           else ->{
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GRAY)
           }

       }
    }

    private fun MostrarDescripcion() {
        edtDescipcionRepFrac.visibility=View.VISIBLE
    }



    fun mensajeReporte(c:Context, txtmensaje:String)
    {
        val dialogoRespuesta = AlertDialog.Builder(c)

        dialogoRespuesta.setTitle(R.string.reportes)
            .setMessage(txtmensaje)
            .setPositiveButton(R.string.msnOk,
                DialogInterface.OnClickListener {dialog, which ->    }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()
    }

}

