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
import com.ghostdust.apphyperspeed.modelos.RespuestaHTPP
import com.ghostdust.apphyperspeed.modelos.Usuario
import com.ghostdust.apphyperspeed.provider.UsuariosProvider
import com.ghostdust.apphyperspeed.utils.Sharepref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registro : AppCompatActivity() {
    val TAG = "Registrarse"
    var imageViewGoToRegister: ImageView? = null
    var TextNombrecompleto: EditText? = null
    var TextEmail: EditText? = null
    var TextPassword: EditText? = null
    var TextPhone: EditText? = null
    var btnRegistro: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        imageViewGoToRegister = findViewById(R.id.go_to_login)
        TextNombrecompleto = findViewById(R.id.text_completon)
        TextEmail = findViewById(R.id.text_correo)
        TextPassword = findViewById(R.id.text_contra)
        TextPhone = findViewById(R.id.text_nume)
        btnRegistro = findViewById(R.id.btn_regi)
        imageViewGoToRegister?.setOnClickListener { goToLogin() }
        btnRegistro?.setOnClickListener { register() }
    }

    private fun register() {
        val name = TextNombrecompleto?.text.toString()
        val email = TextEmail?.text.toString()
        val phone = TextPhone?.text.toString()
        val password = TextPassword?.text.toString()

        if (isValidForm(name, email, phone, password)) {
            val usuario = Usuario(
                email = email,
                nombre = name,
                telefono = phone,
                contraseña = password,
            )

            UsuariosProvider().register(usuario)?.enqueue(object : Callback<RespuestaHTPP> {
                override fun onResponse(call: Call<RespuestaHTPP>, response: Response<RespuestaHTPP>) {

                    if (response.body()?.isSuccess == true){
                        saveusuariosession(response.body()?.data.toString())
                        gotoClienteHome()
                    }

                    Toast.makeText(this@Registro,response.body()?.message, Toast.LENGTH_LONG).show()
                    Log.d(TAG,"Response: ${response}")
                    Log.d(TAG, "Body: ${response.body()}")
                }

                override fun onFailure(call: Call<RespuestaHTPP>, t: Throwable) {
                    Log.d(TAG, "Se produjo un error: ${t.message}")
                    Toast.makeText(this@Registro,"Se produjo un error ${t.message}", Toast.LENGTH_LONG).show()
                }

            })

        } else {
            Toast.makeText(this, "El formulario no es válido", Toast.LENGTH_LONG).show()
        }
    }
    private fun gotoClienteHome(){
        val ie = Intent(this, SaveImagenActivity::class.java)
        ie.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(ie)

    }
    private fun saveusuariosession(data: String?) {
        if (data != null) {
            val sharepref = Sharepref(this)
            val gson = Gson()
            val user = gson.fromJson(data, Usuario::class.java)
            sharepref.save("usuario", user)
        }
    }

    fun String.isEmailValid(): Boolean { //Campos de validacion
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        return !TextUtils.isEmpty(this) && emailPattern.matcher(this).matches()
    }
    private fun isValidForm(name: String, email: String, phone: String, password: String): Boolean {
        if (name.isBlank()) {
            Toast.makeText(this, "Debes ingresar el nombre completo", Toast.LENGTH_LONG).show()
            return false
        }
        if (email.isBlank() || !email.isEmailValid()) {
            Toast.makeText(this, "Debes ingresar un email válido", Toast.LENGTH_LONG).show()
            return false
        }
        if (phone.isBlank()) {
            Toast.makeText(this, "Debes ingresar un teléfono", Toast.LENGTH_LONG).show()
            return false
        }
        if (password.isBlank()) {
            Toast.makeText(this, "Debes ingresar una contraseña", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun goToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}