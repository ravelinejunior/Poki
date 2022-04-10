package br.com.raveline.poki.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.raveline.poki.R
import br.com.raveline.poki.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailBinding = FragmentDetailBinding.inflate(inflater,container,false)

        detailBinding.textView.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_mainFragment)
        }

        findNavController().popBackStack(R.id.action_detailFragment_to_mainFragment,true)

        return detailBinding.root
    }

}