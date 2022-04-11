package br.com.raveline.poki.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("base_experience")
    val baseExp: Int = 0,
    @SerializedName("weight")
    val weight: Float = 0f,
    @SerializedName("species")
    val species: Species = Species(),
    @SerializedName("sprites")
    val sprites: Sprites = Sprites()
):Serializable