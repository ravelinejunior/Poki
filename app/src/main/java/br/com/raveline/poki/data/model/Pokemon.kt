package br.com.raveline.poki.data.model


import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("species")
    val species: Species = Species(),
    @SerializedName("sprites")
    val sprites: Sprites = Sprites()
)