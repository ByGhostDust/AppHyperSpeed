package com.ghostdust.apphyperspeed.Rutas


import com.ghostdust.apphyperspeed.modelos.RespuestaHTPP
import com.ghostdust.apphyperspeed.modelos.Usuario
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface usuariorutas {
    @POST("usuarios/create")
    fun registro(@Body usuario: Usuario): Call<RespuestaHTPP>
    @FormUrlEncoded
    @POST("usuarios/login")
    fun login(@Field("email") email: String, @Field("contraseña")constraseña:String):Call<RespuestaHTPP>
    @Multipart
    @PUT("usuarios/update")
    fun update(
        @Part image: MultipartBody.Part,
        @Part("usuario") usuario: RequestBody): Call<RespuestaHTPP>

}
