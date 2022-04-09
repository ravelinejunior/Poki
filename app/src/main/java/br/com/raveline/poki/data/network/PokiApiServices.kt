package br.com.raveline.poki.data.network

import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.data.model.Pokemons
import br.com.raveline.poki.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokiApiServices {
    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset:Int = 20,
        @Query("limit") limit:Int = 20,
    ):Response<Pokemons>

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id:Int
    ):Response<Pokemon>
}