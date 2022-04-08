package br.com.raveline.poki.data.model


import com.google.gson.annotations.SerializedName

data class PokeType(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)