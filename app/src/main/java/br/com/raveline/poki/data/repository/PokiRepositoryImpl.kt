package br.com.raveline.poki.data.repository

import androidx.annotation.WorkerThread
import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.data.model.Pokemons
import br.com.raveline.poki.data.network.PokiApiPostServices
import br.com.raveline.poki.data.network.PokiApiServices
import br.com.raveline.poki.data.request.PokeRequest
import br.com.raveline.poki.data.request.PokeResponse
import retrofit2.Response
import javax.inject.Inject

class PokiRepositoryImpl @Inject constructor(
    private val apiServices: PokiApiServices,
    private val apiPostServices: PokiApiPostServices
) : PokiRepository {

    @WorkerThread
    override suspend fun getPokemons(offset: Int?, limit: Int?): Response<Pokemons> {
        return apiServices.getPokemon(offset, limit)
    }

    @WorkerThread
    override suspend fun getPokemonById(id: Int): Response<Pokemon> {
        return apiServices.getPokemonById(id)
    }

    override suspend fun postPokemon(pokeRequest: PokeRequest): Response<PokeResponse> {
        return apiPostServices.postPokemon(pokeRequest)
    }
}