package br.com.raveline.poki.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.raveline.poki.R
import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.databinding.FragmentDetailBinding
import br.com.raveline.poki.presentation.viewmodel.PokiViewModel
import br.com.raveline.poki.presentation.viewmodel.PokiViewModelFactory
import br.com.raveline.poki.utils.SUCCESS_CODE_RESPONSE
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var pokiViewModelFactory: PokiViewModelFactory

    private val pokiViewModel: PokiViewModel by viewModels {
        pokiViewModelFactory
    }

    private lateinit var detailBinding: FragmentDetailBinding
    private val pokemonArgs: DetailFragmentArgs by navArgs()

    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        detailBinding.pokemon = pokemonArgs.pokemon
        displayData(pokemonArgs.pokemon)

        pokiViewModel.pokeResponse.observe(viewLifecycleOwner){ pokeResponse ->

            if(pokeResponse.status == SUCCESS_CODE_RESPONSE && !isClicked){
                detailBinding.imageViewDetailFragmentFavoriteId.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_favorite_24)
                )
                isClicked = !isClicked
                Toast.makeText(context, "${pokemonArgs.pokemon.name} ${pokeResponse.support}", Toast.LENGTH_LONG).show()
            }else{
                detailBinding.imageViewDetailFragmentFavoriteId.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_border_24)
                )
                isClicked = !isClicked
            }

        }

        findNavController().popBackStack(R.id.action_detailFragment_to_mainFragment, true)

        return detailBinding.root
    }

    @SuppressLint("SetTextI18n")
    private fun displayData(pokemon: Pokemon) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            pokemonArgs.pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

        detailBinding.apply {
            Glide.with(requireContext()).load(pokemon.sprites.other.officialArtwork.frontDefault)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewDetailFragmentSavedId)

            textViewTitleDetailFragmentId.text = pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            textViewHeightDetailFragmentId.text = "${pokemon.height} feet"
            textViewWeightDetailFragmentId.text = "${pokemon.weight} lb"
            textViewBaseExpDetailFragmentId.text = "${pokemon.baseExp} points"
            textViewOrderDetailFragmentId.text = "${pokemon.order}º"

            linearLayoutDetailFragmentId.setOnClickListener(this@DetailFragment)
            imageViewDetailFragmentFavoriteId.setOnClickListener(this@DetailFragment)


        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            detailBinding.linearLayoutDetailFragmentId.id,
            detailBinding.imageViewDetailFragmentFavoriteId.id -> {
                pokiViewModel.postPokemon(pokemonArgs.pokemon)
            }
        }
    }

}