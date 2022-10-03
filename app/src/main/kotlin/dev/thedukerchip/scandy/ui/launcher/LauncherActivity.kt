package dev.thedukerchip.scandy.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.scandy.ui.scanner.ScannerActivity
import kotlinx.coroutines.delay
import scandy.R

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        lifecycleScope.launchWhenCreated {
            delay(2_000)
            openScanner()
        }
    }

    private fun openScanner() {
        startActivity(Intent(this, ScannerActivity::class.java))
        finish()
    }
}
