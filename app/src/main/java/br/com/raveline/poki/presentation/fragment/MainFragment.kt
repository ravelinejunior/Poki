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
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.poki.databinding.FragmentMainBinding
import br.com.raveline.poki.presentation.adapter.PokiAdapter
import br.com.raveline.poki.presentation.viewmodel.PokiViewModel
import br.com.raveline.poki.presentation.viewmodel.PokiViewModelFactory
import br.com.raveline.poki.presentation.viewmodel.UiState
import br.com.raveline.poki.utils.DEFAULT_OFFSET_PAGE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private var lastVisibleItemPosition: Int = 0


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
        verifyLastData()
    }

    private fun setupRecyclerView() {
        mainBinding.recyclerViewDetailFragment.apply {
            setHasFixedSize(true)
            setHasTransientState(true)
            layoutManager = GridLayoutManager(context, 2)
            // layoutManager = LinearLayoutManager(context)
            adapter = pokiAdapter
        }
    }

    private fun verifyLastData() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!mainBinding.recyclerViewDetailFragment.canScrollVertically(1)) {
                    pokiViewModel.getPokemon(DEFAULT_OFFSET_PAGE)
                }

            }
        }

        mainBinding.recyclerViewDetailFragment.apply {
            addOnScrollListener(scrollListener)
        }

    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            pokiViewModel.uiStateFlow.collect { state ->
                when (state) {
                    UiState.Initial,
                    UiState.Loading -> {
                        mainBinding.apply {

                            progressBarDetailFragment.visibility = VISIBLE
                        }
                    }

                    UiState.Success -> {
                        mainBinding.apply {
                            recyclerViewDetailFragment.visibility = GONE
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