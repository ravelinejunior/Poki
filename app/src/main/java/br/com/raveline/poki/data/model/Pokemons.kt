package br.com.raveline.poki.data.model


import com.google.gson.annotations.SerializedName

data class Pokemons(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: String = "",
    @SerializedName("previous")
    val previous: Any? = null,
    @SerializedName("results")
    val results: List<Result> = listOf()
)