package br.com.raveline.poki.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.poki.R
import br.com.raveline.poki.data.repository.PokiRepositoryImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PokiViewModelFactory @Inject constructor(
    private val repositoryImpl: PokiRepositoryImpl,
    @ApplicationContext private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokiViewModel::class.java)) {
            return PokiViewModel(
                repositoryImpl,
                context
            ) as T
        }
        throw IllegalArgumentException(context.getString(R.string.class_doesnt_match_msg))
    }

}