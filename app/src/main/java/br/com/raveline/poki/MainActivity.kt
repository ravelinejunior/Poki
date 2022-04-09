package br.com.raveline.poki

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import br.com.raveline.poki.databinding.ActivityMainBinding
import br.com.raveline.poki.presentation.viewmodel.PokiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val pokiViewModel: PokiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(mainBinding.root)

        lifecycleScope.launchWhenStarted {
            pokiViewModel.getPokemon()
        }
    }
}