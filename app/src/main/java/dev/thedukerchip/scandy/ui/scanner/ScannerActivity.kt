package dev.thedukerchip.scandy.ui.scanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dev.thedukerchip.scandy.camera.BarcodeAnalyzer
import dev.thedukerchip.scandy.camera.OnBarcodeDetected
import dev.thedukerchip.scandy.extensions.*
import dev.thedukerchip.scandy.permissions.PermissionWrapper
import scandy.databinding.ActivityScannerBinding

@ExperimentalGetImage
class ScannerActivity : FragmentActivity() {

    private lateinit var binding: ActivityScannerBinding

    private val cameraPermission = PermissionWrapper(baseContext, Manifest.permission.CAMERA)
    private var cameraController: LifecycleCameraController? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageAnalysis: ImageAnalysis? = null

    private val permissionHandler =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { accepted ->
            if (accepted) {
                startCamera()
            }
        }

    private val onBarcodeDetected: OnBarcodeDetected = {
        cameraProvider?.unbind(imageAnalysis)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCameraController()
        initViews()
        checkCameraAccess()
    }

    private fun initCameraController() {
        cameraController = LifecycleCameraController(this)
        cameraController?.bindToLifecycle(this)
    }

    private fun initViews() {
        binding.barcodeFinderPv.controller = cameraController
        binding.scannerGrp.gone()

        binding.cameraAccessBtn.setOnClickListener(::requestCameraAccess)
        binding.torchBtn.setOnClickListener(::toggleTorchState)
    }

    private fun checkCameraAccess() {
        if (cameraPermission.isGranted) {
            startCamera()
        } else {
            permissionHandler.launch(Manifest.permission.CAMERA)
        }
    }

    private fun requestCameraAccess() {
        if (cameraPermission.shouldShowRationale) {
            permissionHandler.launch(Manifest.permission.CAMERA)
        } else {
            openApplicationSettings()
        }
    }

    private fun toggleTorchState() {
        cameraController?.let {
            val currentState = it.torchState.value == TorchState.ON
            it.enableTorch(!currentState)
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

        with(ProcessCameraProvider.getInstance(this)) {
            addListener(ContextCompat.getMainExecutor(this@ScannerActivity)) {
                onCameraProviderPrepared(get())
            }
        }
    }

    private fun makeScannerVisible() {
        binding.permissionInfoGrp.gone()
        binding.scannerGrp.visible()
    }

    private fun onCameraProviderPrepared(provider: ProcessCameraProvider) {
        this.cameraProvider = provider
        with(provider) {
            this@ScannerActivity.imageAnalysis = getImagerAnalyzer()
            try {
                unbindAll()
                bindToLifecycle(
                    this@ScannerActivity,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    this@ScannerActivity.imageAnalysis,
                    getPreviewConfig()
                )
            } catch (ex: Exception) {
                Log.e("Scanner", "Unable to bind the camera", ex)
            }
        }
    }

    private fun getPreviewConfig(): Preview {
        return Preview.Builder().build().also {
            it.setSurfaceProvider(binding.barcodeFinderPv.surfaceProvider)
        }
    }

    private fun getImagerAnalyzer(): ImageAnalysis {
        val analyzer = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        analyzer.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            BarcodeAnalyzer(onBarcodeDetected)
        )

        return analyzer
    }
}