package dev.thedukerchip.scandy.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import scandy.databinding.FragmentScannedHistoryBinding

@AndroidEntryPoint
class ScannedHistoryFragment : Fragment() {

    private lateinit var binding: FragmentScannedHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannedHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}