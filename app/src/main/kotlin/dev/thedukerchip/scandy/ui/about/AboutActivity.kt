package dev.thedukerchip.scandy.ui.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import dev.thedukerchip.scandy.ui.BaseActivity
import scandy.databinding.ActivityAboutBinding

class AboutActivity: BaseActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.privacyPolicyLinkTv.movementMethod = LinkMovementMethod.getInstance()
    }
}