package dev.thedukerchip.barcodebuddy.ui.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.barcodebuddy.databinding.ActivityLauncherBinding

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNav()
    }

    private fun initBottomNav() {
        binding.navMenu.setupWithNavController(binding.navHost.navController)
    }

    private val FragmentContainerView.navController: NavController
        get() = (supportFragmentManager.findFragmentById(this.id) as NavHostFragment).navController

}
