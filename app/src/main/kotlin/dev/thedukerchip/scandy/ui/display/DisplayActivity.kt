package dev.thedukerchip.scandy.ui.display

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import dev.thedukerchip.scandy.camera.ScandyBarcode
import dev.thedukerchip.scandy.extensions.copyToClipboard
import dev.thedukerchip.scandy.ui.scanner.CODE
import scandy.databinding.ActivityDisplayBinding


class DisplayActivity : FragmentActivity() {

    private lateinit var binding: ActivityDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.copy.setOnClickListener {
            copyToClipboard("Scanned text", binding.displayValue.text.toString())
        }

        val code = intent.getSerializableExtra(CODE) as? ScandyBarcode
        if (code != null) {
            when (code) {
                is ScandyBarcode.Link -> {
                    showBrowse(code.url)
                    Log.i("Display", "Link: ${code.url}")
                }
                is ScandyBarcode.Text -> {
                    binding.displayValue.text = code.text
                    Log.i("Display", "Text: ${code.text}")
                }
                ScandyBarcode.Unknown -> {
                    Log.w("Display", "Unknown")
                }
            }
        } else {
            Log.e("Display", "Invalid details")
        }
    }

    private fun showBrowse(url: String?) {
        val browseIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (browseIntent.resolveActivity(packageManager) != null) {
            if (url != null) {
                binding.displayValue.text = url
                binding.browse.show()
                binding.browse.setOnClickListener {
                    startActivity(browseIntent)
                }
                startActivity(browseIntent)
            }
        }
    }
}