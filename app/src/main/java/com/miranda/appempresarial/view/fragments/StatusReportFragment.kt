package com.miranda.appempresarial.view.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.InboxReport

import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Internet
import kotlinx.android.synthetic.main.fragment_status_report.*

/**
 * A simple [Fragment] subclass.
 */
class StatusReportFragment : Fragment() {
    var mCallBack: StatusListener?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_report, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if((activity?.let { Internet.coprobarInternet(it) }!!)){
            val datosEmpleado: InboxReport = InboxReport(Consumo.TuNumeroDeEmpleado)
            Consumo.mostrar_reportes(datosEmpleado, activity!!, "Reportes",view )

            btnRegresarListReport.setOnClickListener {
                if(mCallBack!= null){
                    mCallBack!!.statusFinishCallback()
                }
            }
        }
    }
    companion object {
        fun newInstance(): StatusReportFragment =
            StatusReportFragment()
    }

    interface StatusListener{
        fun statusFinishCallback()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mCallBack=activity as StatusListener?
        }catch (e :Exception){}
    }

}
