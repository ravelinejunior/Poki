package br.com.raveline.poki.data.repository

import androidx.annotation.WorkerThread
import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.data.model.Pokemons
import br.com.raveline.poki.data.network.PokiApiServices
import retrofit2.Response
import javax.inject.Inject

class PokiRepositoryImpl @Inject constructor(
    private val apiServices: PokiApiServices
) : PokiRepository {

    @WorkerThread
    override suspend fun getPokemons(offset:Int?,limit:Int?): Response<Pokemons> {
        return apiServices.getPokemon(offset,limit)
    }

    @WorkerThread
    override suspend fun getPokemonById(id: Int): Response<Pokemon> {
        return apiServices.getPokemonById(id)
    }
}