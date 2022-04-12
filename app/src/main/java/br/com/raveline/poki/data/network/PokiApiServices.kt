package br.com.raveline.poki.data.network

import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.data.model.Pokemons
import br.com.raveline.poki.data.model.Result
import br.com.raveline.poki.data.request.PokeRequest
import br.com.raveline.poki.data.request.PokeResponse
import retrofit2.Response
import retrofit2.http.*

interface PokiApiServices {
    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset:Int?,
        @Query("limit") limit:Int? = 20,
    ):Response<Pokemons>

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id:Int
    ):Response<Pokemon>

}