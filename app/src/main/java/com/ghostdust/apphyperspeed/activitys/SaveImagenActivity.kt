package com.ghostdust.apphyperspeed.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.ghostdust.apphyperspeed.R
import com.ghostdust.apphyperspeed.activitys.cliente.home.ClientehomeActivity
import com.ghostdust.apphyperspeed.modelos.RespuestaHTPP
import com.ghostdust.apphyperspeed.modelos.Usuario
import com.ghostdust.apphyperspeed.provider.UsuariosProvider
import com.ghostdust.apphyperspeed.utils.Sharepref
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SaveImagenActivity : AppCompatActivity() {

    val  TAG = "SaveImagenActivity"

    var circleImageUser: CircleImageView? = null
    var buttonNext: Button? = null
    var buttonConfirm: Button? = null

    private var imageFile: File? = null
    var user: Usuario? = null
    var usersProvier = UsuariosProvider()

    var sharepref: Sharepref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_imagen)

        sharepref = Sharepref(this)

        getUsuariofromSession()


        circleImageUser = findViewById(R.id.cimagen_user)
        buttonNext = findViewById(R.id.btn_confirm)
        buttonConfirm = findViewById(R.id.btn_confirm)


        circleImageUser?.setOnClickListener{selectImage()}

        buttonNext?.setOnClickListener{goToClienteHome()}
        buttonConfirm?.setOnClickListener { saveImage() }
    }
    private fun saveImage(){

        if(imageFile != null && user != null){
            usersProvier.update(imageFile!!, user!!)?.enqueue(object: Callback<RespuestaHTPP> {
                override fun onResponse(call: Call<RespuestaHTPP>, response: Response<RespuestaHTPP>) {
                    Log.d(TAG,"Respuesta: $response")
                    Log.d(TAG,"BODY: ${response.body()}")

                }

                override fun onFailure(call: Call<RespuestaHTPP>, t: Throwable) {
                    Log.d(TAG,"Error: ${t.message}")
                    Toast.makeText(this@SaveImagenActivity,"Error: ${t.message}", Toast.LENGTH_LONG).show()
                }

            })
        }
        else {
            Log.e(TAG, "La imagen o los datos de sesión del usuario son nulos")
            Toast.makeText(this,"La imagen no puede ser nula ni tampoco los datos de sesion del usuario", Toast.LENGTH_LONG).show()

        }

    }
    private fun goToClienteHome() {
        val ie = Intent(this, ClientehomeActivity::class.java)
        ie.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas
        startActivity(ie)
    }
    private fun getUsuariofromSession() {
        val gson = Gson()
        if (!sharepref?.getData("usuario").isNullOrBlank()) {
            //Si el usuario existe en session
            user = gson.fromJson(sharepref?.getData("usuarios"), Usuario::class.java)
        }
    }
    private val startImageForResult =registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: androidx.activity.result.ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data
        if(resultCode == Activity.RESULT_OK){
            val fileUri = data?.data
            imageFile = File(fileUri?.path)//El archivo que vamos a guardar en el servidor
            circleImageUser?.setImageURI(fileUri)
        }
        else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"La tarea se cancelo", Toast.LENGTH_LONG).show()

        }
    }
    private fun selectImage(){
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080,1080)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }

    }
}