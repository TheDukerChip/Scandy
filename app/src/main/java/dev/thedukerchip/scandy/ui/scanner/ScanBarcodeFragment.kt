package dev.thedukerchip.scandy.ui.scanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.scandy.permissions.PermissionWrapper
import dev.thedukerchip.scandy.ui.extensions.gone
import dev.thedukerchip.scandy.ui.extensions.openApplicationSettings
import dev.thedukerchip.scandy.ui.extensions.visible
import scandy.databinding.FragmentScanBarcodeBinding
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalGetImage
class ScanBarcodeFragment : Fragment() {

    @Inject
    lateinit var barcodeImageAnalyzer: ImageAnalysis

    private lateinit var binding: FragmentScanBarcodeBinding
    private val cameraPermission = PermissionWrapper(this, Manifest.permission.CAMERA)

    private val permissionHandler =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { accepted ->
            if (accepted) {
                startCamera()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBarcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scannerGrp.gone()
        if (cameraPermission.isGranted) {
            startCamera()
        } else {
            permissionHandler.launch(Manifest.permission.CAMERA)
            binding.cameraAccessBtn.setOnClickListener {
                if (cameraPermission.shouldShowRationale) {
                    permissionHandler.launch(Manifest.permission.CAMERA)
                } else {
                    context?.openApplicationSettings()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (cameraPermission.isGranted) {
            startCamera()
        }
    }

    private fun startCamera() {
        makeScannerVisible()

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.barcodeFinderPv.surfaceProvider)
            }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, barcodeImageAnalyzer, preview)
            } catch (ex: Exception) {
                Log.e("Scanner", "Unable to bind the camera", ex)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun makeScannerVisible() {
        binding.permissionInfoGrp.gone()
        binding.scannerGrp.visible()
    }
}