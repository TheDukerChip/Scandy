package dev.thedukerchip.barcodebuddy.camera

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@ExperimentalGetImage
@FragmentScoped
class BarcodeAnalyzer @Inject constructor() : ImageAnalysis.Analyzer {
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val scanner = BarcodeScanning.getClient()
            scanner.process(image).addOnSuccessListener {
                if (it.isNotEmpty()) {
                    it.forEach { barcode ->
                        Log.i("Barcode", barcode.rawValue)
                    }
                }
                imageProxy.close()
            }.addOnFailureListener { exception ->
                Log.e("Barcode", "Unable to parse the barcode", exception)
                imageProxy.close()
            }
        }
    }
}