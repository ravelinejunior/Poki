package br.com.raveline.poki.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.data.model.Pokemons
import br.com.raveline.poki.data.repository.PokiRepositoryImpl
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

    private val mutablePokiList = MutableLiveData<Pokemons>()
    val pokiListLiveData: LiveData<Pokemons> get() = mutablePokiList

    private val mutablePokemon = MutableLiveData<Pokemon>()
    val pokemonLiveData: LiveData<Pokemon> get() = mutablePokemon

    val pokemons = arrayListOf<Pokemon>()

    init {
        getPokemon()
    }

    private fun getPokemon() {
        _uiStateFlow.value = UiState.Loading

        viewModelScope.launch {
            if (SystemFunctions.isNetworkAvailable(context)) {

                val pokiResponse = repository.getPokemons()
                if (pokiResponse.isSuccessful) {
                    val body = pokiResponse.body()
                    body?.let { _pokemons ->
                        mutablePokiList.value = _pokemons
                        _pokemons.results.forEach { result ->
                            val pokemonResponse = repository.getPokemonById(result.url.decodeURL())
                            if (pokemonResponse.isSuccessful) {
                                val pokemon = pokemonResponse.body()
                                pokemons.add(pokemon!!)
                                Log.d("TAGPokemon", pokemonLiveData.value.toString()+"\n\n" )
                            } else {
                                _uiStateFlow.value = UiState.Error
                            }
                        }
                    }
                    _uiStateFlow.value = UiState.Success
                } else {
                    _uiStateFlow.value = UiState.Error
                }

            } else {
                _uiStateFlow.value = UiState.NoConnection
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