package br.com.raveline.poki.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.raveline.poki.databinding.FragmentMainBinding
import br.com.raveline.poki.presentation.adapter.PokiAdapter
import br.com.raveline.poki.presentation.viewmodel.PokiViewModel
import br.com.raveline.poki.presentation.viewmodel.PokiViewModelFactory
import br.com.raveline.poki.presentation.viewmodel.UiState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var mainBinding: FragmentMainBinding

    private val pokiAdapter: PokiAdapter by lazy {
        PokiAdapter()
    }

    @Inject
    lateinit var pokiViewModelFactory: PokiViewModelFactory

    private val pokiViewModel: PokiViewModel by viewModels {
        pokiViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        mainBinding.lifecycleOwner = this
        setupRecyclerView()
        findNavController().navigateUp()


        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservables()
    }

    private fun setupRecyclerView() {
        mainBinding.recyclerViewDetailFragment.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            // layoutManager = LinearLayoutManager(context)
            adapter = pokiAdapter
        }
    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            pokiViewModel.uiStateFlow.collect { state ->
                when (state) {
                    UiState.Initial,
                    UiState.Loading -> {
                        mainBinding.apply {
                            recyclerViewDetailFragment.visibility = GONE
                            progressBarDetailFragment.visibility = VISIBLE
                        }
                    }

                    UiState.Success -> {
                        mainBinding.apply {
                            recyclerViewDetailFragment.visibility = VISIBLE
                            progressBarDetailFragment.visibility = GONE
                        }

                        pokiAdapter.setData(pokiViewModel.pokemons)
                    }
                    UiState.Error -> {
                        // TODO: Create lottie
                    }
                    UiState.NoConnection -> {
                        // TODO: Create lottie
                    }
                }
            }
        }
    }

}