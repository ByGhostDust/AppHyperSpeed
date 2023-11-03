package com.ghostdust.apphyperspeed.modelos

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class RespuestaHTPP(
    @SerializedName("message") val message: String,
    @SerializedName("success") val isSuccess: Boolean,
    @SerializedName("data") val data: JsonObject,
    @SerializedName("error") val error: String
) {
    override fun toString(): String {
        return "RespuestaHTPP(message='$message', isSuccess=$isSuccess, data=$data, error='$error')"
    }
}
