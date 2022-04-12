package br.com.raveline.poki.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.data.repository.PokiRepositoryImpl
import br.com.raveline.poki.data.request.PokeRequest
import br.com.raveline.poki.data.request.PokeResponse
import br.com.raveline.poki.utils.SystemFunctions
import br.com.raveline.poki.utils.SystemFunctions.decodeURL
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokiViewModel @Inject constructor(
    private val repository: PokiRepositoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    private val mutablePage = MutableLiveData(0)
    private val pageLiveData: LiveData<Int> get() = mutablePage

    private val mutablePokemonResponse = MutableLiveData<PokeResponse>()
    val pokeResponse: LiveData<PokeResponse> get() = mutablePokemonResponse

    val pokemons = arrayListOf<Pokemon>()

    init {
        getPokemon(null)
    }

    fun getPokemon(offset: Int?) {
        _uiStateFlow.value = UiState.Loading

        viewModelScope.launch {
            if (SystemFunctions.isNetworkAvailable(context)) {

                if (offset == null) {
                    getData()
                } else {
                    pagination(pageLiveData.value!!)
                }

            } else {
                _uiStateFlow.value = UiState.NoConnection
            }
        }

    }

    private fun pagination(offset: Int) {
        mutablePage.value = offset + 20

        viewModelScope.launch {
            getData(pageLiveData.value)
        }
    }

    private fun getData(offset: Int? = null) {
        viewModelScope.launch {
            val pokiResponse = repository.getPokemons(offset)
            if (pokiResponse.isSuccessful) {
                val body = pokiResponse.body()
                body?.let { _pokemons ->
                    if (_pokemons.count > pageLiveData.value!!) {
                        _pokemons.results.forEach { result ->
                            val pokemonResponse = repository.getPokemonById(result.url.decodeURL())
                            if (pokemonResponse.isSuccessful) {
                                val pokemon = pokemonResponse.body()
                                pokemons.add(pokemon!!)
                            } else {
                                _uiStateFlow.value = UiState.Error
                            }
                        }
                    }

                }
                _uiStateFlow.value = UiState.Success
            } else {
                _uiStateFlow.value = UiState.Error
            }
        }
    }

    fun postPokemon(pokemon: Pokemon) {
        val pokeRequest = PokeRequest()
        viewModelScope.launch {
            val response = repository.postPokemon(pokeRequest)
            if (response.isSuccessful) {
                mutablePokemonResponse.value = response.body()
                Log.e("TAGPostPokemon", pokemon.toString())
            } else {
                Log.e("TAGPostPokemon", response.message())
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

sealed class UiState {
    object Success : UiState()
    object Error : UiState()
    object NoConnection : UiState()
    object Loading : UiState()
    object Initial : UiState()
}