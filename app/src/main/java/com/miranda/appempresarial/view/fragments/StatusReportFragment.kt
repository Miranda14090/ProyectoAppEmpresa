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

/**
 * A simple [Fragment] subclass.
 */
class StatusReportFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_report, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var datosEmpleado: InboxReport = InboxReport("000001")
        Consumo.mostrar_reportes(datosEmpleado!!, activity!!, "",view )
    }
    companion object {
        fun newInstance(): StatusReportFragment =
            StatusReportFragment()
    }

}
