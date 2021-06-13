package dev.thedukerchip.barcodebuddy.ui.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.barcodebuddy.R
import dev.thedukerchip.barcodebuddy.databinding.ActivityLauncherBinding

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_launcher)
    }
}