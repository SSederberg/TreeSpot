package net.n4dev.treespot.ui.main.fragments.capture

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface.ROTATION_90
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.orhanobut.logger.Logger
import net.n4dev.treespot.core.api.ITreeSpotMedia
import net.n4dev.treespot.core.entity.TreeSpotMedia
import net.n4dev.treespot.core.entity.TypeConst
import net.n4dev.treespot.databinding.FragmentCaptureSpotBinding
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.spots.addspot.AddSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import java.io.File
import java.util.concurrent.ExecutionException


/**
 * A fragment to take a picture from the user's TreeSpot.
 */
class CaptureSpotFragment() : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding : FragmentCaptureSpotBinding
    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() =  _binding!!
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var pictureDir : File
    private val cameraRequestCode = 6066
    private var imageCount = 0
    private var imagesCaptured = ArrayList<ITreeSpotMedia>()
    private lateinit var userID : String

    constructor(userID : String) : this() {
        this.userID = userID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCaptureSpotBinding.inflate(inflater, container, false)

        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),  TreeSpotActivity.TREESPOT_PERMISSIONS, cameraRequestCode)
        } else {
            setupCamera()
        }

        binding.captureSpotAction.setOnClickListener {
            if(imageCount == 0) {
                takePicture()
                imageCount++
                ActivityUtil.toast(requireContext(), "Take two more pictures to capture this spot!", false)
            } else if(imageCount == 1) {
               takePicture()
               imageCount++
               ActivityUtil.toast(requireContext(), "Take one more picture to capture this spot!", false)
           } else if(imageCount == 2) {
               takePicture()

                val bundle = Bundle()
                val mediaArray : ArrayList<String> = ArrayList();
                imagesCaptured.forEach {
                    mediaArray.add(it.getMediaPath())
                }

                bundle.putStringArrayList(AddSpotActivity.ARG_IMAGES_ARRAY, mediaArray)
                bundle.putString(AddSpotActivity.ARG_USER_ID, userID)
                ActivityUtil.startActivity(bundle, AddSpotActivity::class.java, requireActivity(), false)
           }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureDir = ActivityUtil.getAppImagesDirectoryAsFile(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        setupCamera()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if(requestCode == cameraRequestCode) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupCamera()
            } else {
                ActivityUtil.snack(binding.root, "Unable to capture your amazing tree spots!", true)
            }
        } else return
    }

    private fun setupCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider
            try {
                cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()

                val cameraSelector = CameraSelector.Builder()
                    .build()

                val preview = Preview.Builder()
                    .setTargetRotation(ROTATION_90)
                    .build().also {
                        it.setSurfaceProvider(binding.captureSpotPreview.surfaceProvider)
                    }

                imageCapture = ImageCapture.Builder()
//                    .setTargetRotation(ROTATION_90)
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                    .build()

                preview.setSurfaceProvider(binding.captureSpotPreview.surfaceProvider)


                cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview)
            } catch (e: ExecutionException) {
                e.printStackTrace()
                Logger.e("An error during camera setup has occurred!", e)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                Logger.e("An error during camera setup has occurred!", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePicture() {
        val imageFile = File(ActivityUtil.getAppImagesDirectory(requireActivity()) + "/" + userID + ".png")
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()
        val errorContext = super.requireContext()
        imageCapture?.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val filename = outputFileResults.savedUri.toString()

                val newMedia = TreeSpotMedia(userID, "NULL", TypeConst.PICTURE, filename, filename)
                imagesCaptured.add(newMedia)

            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
                ActivityUtil.toast(errorContext, "Fail to capture your spot!", true)
            }

        })
    }
}