package dev.thedukerchip.barcodebuddy.di.camera

import android.content.Context
import android.util.Size
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.thedukerchip.barcodebuddy.camera.BarcodeAnalyzer

@Module
@InstallIn(FragmentComponent::class)
class CameraModule {
    @ExperimentalGetImage
    @Provides
    fun providerBarcodeImageAnalyzer(@ApplicationContext context: Context): ImageAnalysis {
        val analyzer = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        analyzer.setAnalyzer(ContextCompat.getMainExecutor(context), BarcodeAnalyzer())

        return analyzer
    }
}