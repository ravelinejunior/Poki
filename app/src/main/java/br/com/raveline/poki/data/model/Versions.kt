package br.com.raveline.poki.data.model


import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i")
    val generationI: GenerationI = GenerationI(),
    @SerializedName("types")
    val types: List<Type> = listOf(),
    @SerializedName("weight")
    val weight: Int = 0
)