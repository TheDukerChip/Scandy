package dev.thedukerchip.barcodebuddy.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.thedukerchip.barcodebuddy.databinding.FragmentScanBarcodeBinding

class ScanBarcodeFragment: Fragment() {
    private lateinit var binding: FragmentScanBarcodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBarcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

}