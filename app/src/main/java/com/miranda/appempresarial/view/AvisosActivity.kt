package com.miranda.appempresarial.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.miranda.appempresarial.R
import kotlinx.android.synthetic.main.activity_avisos.*

class AvisosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avisos)

        txtCuerpo.setMovementMethod(ScrollingMovementMethod())
    }
}
