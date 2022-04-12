package br.com.raveline.poki.data.request


import com.google.gson.annotations.SerializedName

data class PokeResponse(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("support")
    val support: String = ""
)