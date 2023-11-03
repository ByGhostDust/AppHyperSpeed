package com.ghostdust.apphyperspeed.activitys.cliente.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.ghostdust.apphyperspeed.R
import com.ghostdust.apphyperspeed.activitys.Login
import com.ghostdust.apphyperspeed.modelos.Usuario
import com.ghostdust.apphyperspeed.utils.Sharepref
import com.google.gson.Gson

class ClientehomeActivity : AppCompatActivity() {
    private val TAG = "ClientehomeActivity"
    var buttonLoGOUT: Button? = null
    var sharePref: Sharepref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientehome)
        sharePref = Sharepref(this)
        buttonLoGOUT = findViewById(R.id.btn_logout)
        buttonLoGOUT?.setOnClickListener {logout()}
        getUsuariofromSession()
    }
    private fun logout() {
        sharePref?.remove("usuario")
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun getUsuariofromSession() {
        val sharepref = Sharepref(this)
        val gson = Gson()
        if (!sharepref.getData("usuario").isNullOrBlank()) {
            //Si el usuario existe en session
            val user = gson.fromJson(sharepref.getData("usuarios"), Usuario::class.java)
            Log.d(TAG,"USUARIO: $user")
        }
    }
}