package br.com.raveline.poki.data.model


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int = 0,
    @SerializedName("type")
    val pokeType: PokeType = PokeType()
)