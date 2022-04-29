package com.example.diplom

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.diplom.databinding.ActivityCameraBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.label.internal.client.ImageLabelerOptions
import com.google.android.gms.vision.text.TextRecognizer
import com.google.firebase.ktx.Firebase
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions.DEFAULT_OPTIONS
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions.DEFAULT_OPTIONS
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.jar.Manifest
import kotlin.system.exitProcess


class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var imageAnalysis: ImageAnalysis? = null

    private val CAMERA_PERMISION_CODE = 111
    private val STORAGE_PERMISSION_CODE = 222
    private val TAG = "myTag"

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    lateinit var inputImage: InputImage

    lateinit var imageLabler: ImageLabeler

    private var cardsAnimals = ArrayList<TypeAnimal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arguments = intent.extras
        if (arguments != null) {
            cardsAnimals = arguments.getSerializable("item") as ArrayList<TypeAnimal>
            cardsAnimals = arguments.getParcelableArray("list") as ArrayList<TypeAnimal>
        }

        imageLabler = ImageLabeling.getClient(com.google.mlkit.vision.label.defaults.ImageLabelerOptions.DEFAULT_OPTIONS)

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            object: ActivityResultCallback<ActivityResult>{
                override fun onActivityResult(result: ActivityResult?) {
                    val data = result?.data
                    try {
                        val photo = data?.extras?.get("data") as Bitmap
                        inputImage = InputImage.fromBitmap(photo, 0)
                        processImage()
                    } catch (e: Exception) {
                        Log.d(TAG, "onActivityResut: ${e.message}")
                    }
                }

            }
        )

        binding.buttonStartCamera.setOnClickListener {
            checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }

        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            object: ActivityResultCallback<ActivityResult>{
                override fun onActivityResult(result: ActivityResult?) {
                    val data = result?.data
                    try {
                        inputImage = InputImage.fromFilePath(this@CameraActivity, data?.data!!)
                        processImage()
                    } catch (e: Exception) {

                    }
                }

            }
        )

        binding.buttonSelectPhoto.setOnClickListener {
            checkPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISION_CODE)
            val storageIntent = Intent()
            storageIntent.setType("image/*")
            storageIntent.setAction(Intent.ACTION_GET_CONTENT)
            galleryLauncher.launch(storageIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISION_CODE)
    }

    private fun processImage() {
        imageLabler.process(inputImage)
            .addOnSuccessListener {
                var result = ""
                for (label in it) {
                    result = result + "\n" + label.text
                }
            }.addOnFailureListener {
                Log.d(TAG, "processImage: ${it.message}")
            }
    }

    fun onBackPressed(view: View) {
        super.onBackPressed()
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@CameraActivity, permission) == PackageManager.PERMISSION_DENIED) {
            /*Take permission*/
            ActivityCompat.requestPermissions(this@CameraActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@CameraActivity, "Permission Granted already", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@CameraActivity, "Camera Permission Granted",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CameraActivity, "Camera Permission Denied", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@CameraActivity, "Storage Permission Granted",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CameraActivity, "Storage Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}

