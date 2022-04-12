package br.com.raveline.poki.data.network

import br.com.raveline.poki.data.request.PokeRequest
import br.com.raveline.poki.data.request.PokeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PokiApiPostServices {
    @POST("pokemon")
    suspend fun postPokemon(
        @Body pokemon: PokeRequest
    ): Response<PokeResponse>
}