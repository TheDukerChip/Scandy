package dev.thedukerchip.scandy.ui.scanner

import android.Manifest
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.scandy.camera.BarcodeAnalyzer
import dev.thedukerchip.scandy.camera.OnBarcodeDetected
import dev.thedukerchip.scandy.extensions.*
import dev.thedukerchip.scandy.permissions.PermissionResult
import dev.thedukerchip.scandy.permissions.permissionWrapperFor
import dev.thedukerchip.scandy.ui.display.DisplayActivity
import scandy.databinding.ActivityScannerBinding

const val CODE = "code"

@AndroidEntryPoint
class ScannerActivity : FragmentActivity() {

    private lateinit var binding: ActivityScannerBinding

    private var cameraController: LifecycleCameraController? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageAnalysis: ImageAnalysis? = null

    private val onPermissionResult = PermissionResult { accepted ->
        if (accepted) {
            startCamera()
        }
    }
    private val cameraPermission =
        permissionWrapperFor(Manifest.permission.CAMERA, onPermissionResult)

    private val onBarcodeDetected: OnBarcodeDetected = { code ->
        startActivity(Intent(this, DisplayActivity::class.java).also {
            it.putExtra(CODE, code)
        })
        cameraProvider?.unbind(imageAnalysis)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setNoLimitsToLayout()

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
        binding.torchBtn.setFlashState(FlashState.OFF)
        binding.torchBtn.setOnClickListener {
            cameraController?.toggleFlashState()
        }

        cameraController?.torchState?.observe(this) {
            binding.torchBtn.setFlashState(FlashState.fromTorchState(it))
        }
    }

    private fun FloatingActionButton.setFlashState(state: FlashState) {
        backgroundTintList = ColorStateList.valueOf(getColor(state.color()))
        setImageDrawable(ContextCompat.getDrawable(context, state.icon()))
    }

    private fun LifecycleCameraController.toggleFlashState() {
        when (torchState.value) {
            TorchState.ON -> enableTorch(false)
            TorchState.OFF -> enableTorch(true)
            else -> throw RuntimeException("Invalid TorchState")
        }
    }

    private fun checkCameraAccess() {
        cameraPermission.check {
            startCamera()
        }
    }

    private fun requestCameraAccess() {
        cameraPermission.request()
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

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
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