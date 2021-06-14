package dev.thedukerchip.scandy.ui.scanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.thedukerchip.scandy.permissions.PermissionWrapper
import dev.thedukerchip.scandy.extensions.*
import scandy.databinding.FragmentScanBarcodeBinding
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalGetImage
class ScanBarcodeFragment : Fragment() {

    @Inject
    lateinit var barcodeImageAnalyzer: ImageAnalysis

    private lateinit var binding: FragmentScanBarcodeBinding

    private val cameraPermission = PermissionWrapper(this, Manifest.permission.CAMERA)
    private var cameraController: LifecycleCameraController? = null

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

        initCameraController()
        initViews()
        checkCameraAccess()
    }

    private fun initCameraController() {
        cameraController = LifecycleCameraController(requireContext())
        cameraController?.bindToLifecycle(viewLifecycleOwner)
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
            context?.openApplicationSettings()
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

        with(ProcessCameraProvider.getInstance(requireContext())) {
            addListener(ContextCompat.getMainExecutor(context)) {
                onCameraProviderPrepared(get())
            }
        }
    }

    private fun makeScannerVisible() {
        binding.permissionInfoGrp.gone()
        binding.scannerGrp.visible()
    }

    private fun onCameraProviderPrepared(provider: ProcessCameraProvider) {
        with(provider) {
            try {
                unbindAll()
                bindToLifecycle(
                    this@ScanBarcodeFragment,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    barcodeImageAnalyzer,
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
}