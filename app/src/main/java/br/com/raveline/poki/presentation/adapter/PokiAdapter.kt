package br.com.raveline.poki.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.poki.data.model.Pokemon
import br.com.raveline.poki.databinding.PokiItemAdapterBinding
import br.com.raveline.poki.utils.ListDiffUtil
import br.com.raveline.poki.utils.SystemFunctions.loadImage
import java.util.*

class PokiAdapter : RecyclerView.Adapter<PokiAdapter.MyViewHolder>() {

    private var pokemons = emptyList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val pBinding =
            PokiItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(pBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.bind(pokemon)
    }

    override fun getItemCount(): Int = pokemons.size

    fun setData(_pokemons: ArrayList<Pokemon>) {
        val diffUtils = ListDiffUtil(pokemons, _pokemons)
        val dispatchersResult = DiffUtil.calculateDiff(diffUtils)
        pokemons = _pokemons
        dispatchersResult.dispatchUpdatesTo(this)
    }


    inner class MyViewHolder(private val pBinding: PokiItemAdapterBinding) :
        RecyclerView.ViewHolder(pBinding.root) {

        fun bind(_pokemon: Pokemon) {
            pBinding.apply {
                textViewTitlePokiItemAdapterId.text = _pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }

                loadImage(
                    imageViewPokiItemAdapterId,
                    _pokemon.sprites.other.officialArtwork.frontDefault
                )
            }
        }

    }
}