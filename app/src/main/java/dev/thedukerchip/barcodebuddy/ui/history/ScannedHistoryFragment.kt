package dev.thedukerchip.barcodebuddy.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.thedukerchip.barcodebuddy.databinding.FragmentScannedHistoryBinding

class ScannedHistoryFragment : Fragment() {

    private lateinit var binding: FragmentScannedHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScannedHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}