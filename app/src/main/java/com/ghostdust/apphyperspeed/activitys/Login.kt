package com.ghostdust.apphyperspeed.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.ghostdust.apphyperspeed.R
import com.ghostdust.apphyperspeed.activitys.administrador.administrador_homeActivity
import com.ghostdust.apphyperspeed.activitys.cliente.home.ClientehomeActivity
import com.ghostdust.apphyperspeed.activitys.repartidor.Repartidor_homeActivity
import com.ghostdust.apphyperspeed.modelos.RespuestaHTPP
import com.ghostdust.apphyperspeed.modelos.Usuario
import com.ghostdust.apphyperspeed.provider.UsuariosProvider
import com.ghostdust.apphyperspeed.utils.Sharepref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private var TextEmail: EditText? = null
    private var TextPassword: EditText? = null
    private var btnLogin: Button? = null
    private var imageViewGoToLogin: ImageView? = null
    private val usuarioprovider = UsuariosProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        TextEmail = findViewById(R.id.text_correol)
        TextPassword = findViewById(R.id.text_contral)
        btnLogin = findViewById(R.id.btn_login)

        imageViewGoToLogin = findViewById(R.id.go_to_register)
        imageViewGoToLogin?.setOnClickListener { goToRegister() }
        btnLogin?.setOnClickListener { login() }
        getUsuariofromSession()
    }

    private fun login() {
        val email = TextEmail?.text.toString()
        val password = TextPassword?.text.toString()

        if (isValidForm(email, password)) {
            usuarioprovider.login(email, password)?.enqueue(object : Callback<RespuestaHTPP> {
                override fun onResponse(call: Call<RespuestaHTPP>, response: Response<RespuestaHTPP>) {
                    Log.d("PLogin", "Response: ${response.body()}")
                    if (response.body()?.isSuccess == true) {
                        Toast.makeText(this@Login, response.body()?.message, Toast.LENGTH_LONG).show()
                        saveusuariosession(response.body()?.data.toString())
                    } else {
                        Toast.makeText(this@Login, "Los datos no son correctos", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RespuestaHTPP>, t: Throwable) {
                    Log.d("PLogin", "Hubo un error ${t.message}")
                    Toast.makeText(this@Login, "Hubo un error ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "El formulario no es válido", Toast.LENGTH_LONG).show()
        }
    }

    private fun goToClienteHome() {
        val ie = Intent(this, ClientehomeActivity::class.java)
        ie.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas
        startActivity(ie)
    }

    private fun goToAdministrarHome() {
        val ie = Intent(this, administrador_homeActivity::class.java)
        ie.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas

        startActivity(ie)
    }

    private fun goToRepartidorHome() {
        val ie = Intent(this, Repartidor_homeActivity::class.java)
        ie.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas

        startActivity(ie)
    }

    private fun goToSelectRol() {
        val ie = Intent(this, SelectRolesActivity::class.java)
        ie.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas

        startActivity(ie)
    }

    private fun saveusuariosession(data: String?) {
        if (data != null) {
            val sharepref = Sharepref(this)
            val gson = Gson()
            val user = gson.fromJson(data, Usuario::class.java)
            sharepref.save("usuario", user)

            if (user.roles?.size!! > 1) {
                goToSelectRol()
            } else {
                goToClienteHome()
            }
        }
    }

    private fun isValidForm(email: String, password: String): Boolean {
        return !email.isBlank() && !password.isBlank() && email.isEmailValid()
    }

    private fun goToRegister() {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }

    private fun getUsuariofromSession() {
        val sharepref = Sharepref(this)
        val gson = Gson()
        if (!sharepref.getData("usuario").isNullOrBlank()) {
            // Si el usuario existe en sesión
            val user = gson.fromJson(sharepref.getData("usuario"), Usuario::class.java)

            val rol = sharepref.getData("rol")?.replace("\"","")

            if (!rol.isNullOrBlank()) {
                // Si el usuario seleccionó el rol
                if (rol == "ADMINISTRADOR") {
                    goToAdministrarHome()
                }
                else  if (rol == "USUARIO") {
                    goToClienteHome()
                }
                else  if (rol == "REPARTIDOR") {
                    goToRepartidorHome()
                }
            }
            else{
                goToClienteHome()
            }
        }
    }

    private fun String.isEmailValid(): Boolean {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        return !TextUtils.isEmpty(this) && emailPattern.matcher(this).matches()
    }
}