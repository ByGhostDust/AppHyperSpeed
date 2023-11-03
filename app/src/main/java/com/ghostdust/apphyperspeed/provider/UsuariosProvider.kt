package com.ghostdust.apphyperspeed.provider

import com.ghostdust.apphyperspeed.Rutas.usuariorutas
import com.ghostdust.apphyperspeed.api.ApiRutas
import com.ghostdust.apphyperspeed.modelos.RespuestaHTPP
import com.ghostdust.apphyperspeed.modelos.Usuario

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class UsuariosProvider {
    private var usersRoutes: usuariorutas? = null

    init {
        val api = ApiRutas()
        usersRoutes = api.getUsersRoutes()
    }

    fun register(user: Usuario): Call<RespuestaHTPP>? {
        return usersRoutes?.registro(user)
    }
    fun login(email: String,contraseña:String): Call<RespuestaHTPP>? {
        return usersRoutes?.login(email,contraseña)
    }
fun update(file: File, usuario: Usuario): Call<RespuestaHTPP>? {


    val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
    val image = MultipartBody.Part.createFormData("imagen",file.name, reqFile)
    val requestBody = RequestBody.create(MediaType.parse("text/plain"),usuario.toJson())

    return usersRoutes?.update(image,requestBody)
}

}
