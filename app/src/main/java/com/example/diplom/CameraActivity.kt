package com.example.diplom

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.diplom.databinding.ActivityCameraBinding
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var imageAnalysis: ImageAnalysis? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this,
                Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(
            Runnable {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)/*оставляем только последнее изображение*/
                    .build()
                    .apply{
                        setAnalyzer(
                            Executors.newSingleThreadExecutor(),
                            MainActivity.CornerAnalyzer {
                                val bitmap = binding.viewFinder.bitmap
                                val img = Mat()
                                Utils.bitmapToMat(bitmap, img)
                                bitmap?.recycle()
                            })
                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK) /*нужна задняя камера*/
                            .build()

//                        try {
//                            cameraProvider.unbindAll()
//                            cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis)
//                        } catch (e: Exception) {
//                            Log.d(Constants.TAG, "startCamera fail:", e)
//                    }
                    }
            }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permission not granted",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}