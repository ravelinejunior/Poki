package br.com.raveline.poki.data.request


import com.google.gson.annotations.SerializedName

data class PokeRequest(
    @SerializedName("base_experience")
    val baseExperience: Int = 100,
    @SerializedName("height")
    val height: Int = 20,
    @SerializedName("name")
    val name: String = "pokemon",
    @SerializedName("order")
    val order: Int = 3
)