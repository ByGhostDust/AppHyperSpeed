package com.ghostdust.apphyperspeed.modelos

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id") val id: String? = null,
    @SerializedName("email") val email: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("contraseña") val contraseña: String,
    @SerializedName("imagen") val imagen: String? = null,
    @SerializedName("is_available") val isAvailable: Boolean? = null,
    @SerializedName("session_token") val sessionToken: String? = null,
    @SerializedName("roles") val roles: ArrayList<Rol>? = null
){
    override fun toString(): String {
        return "Usuario(id=$id, email='$email', nombre='$nombre', telefono='$telefono', contraseña='$contraseña', imagen=$imagen, isAvailable=$isAvailable, sessionToken=$sessionToken, roles=$roles)"
    }

    fun toJson(): String{
     return Gson().toJson(this)
    }
}