package com.ghostdust.apphyperspeed.modelos

import com.google.gson.annotations.SerializedName

class Rol(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("imagen") val imagen: String,
    @SerializedName("route") val route: String?
) {
    override fun toString(): String {
        return "Rol(id='$id', nombre='$nombre', imagen='$imagen', route='$route')"
    }
}
