package br.com.raveline.poki.data.repository

import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.data.model.Pokemons
import retrofit2.Response

interface PokiRepository {
    suspend fun getPokemons(): Response<Pokemons>
    suspend fun getPokemonById(id: Int): Response<Pokemon>
}