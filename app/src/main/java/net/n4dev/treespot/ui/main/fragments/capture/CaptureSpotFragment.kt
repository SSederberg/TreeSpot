package net.n4dev.treespot.ui.main.fragments.capture

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.orhanobut.logger.Logger
import net.n4dev.treespot.databinding.FragmentCaptureSpotBinding
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import java.io.File
import java.util.concurrent.ExecutionException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A fragment to take a picture from the user's TreeSpot.
 */
class CaptureSpotFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private var _binding : FragmentCaptureSpotBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() =  _binding!!
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var pictureDir : File
    private val cameraRequestCode = 6066

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCaptureSpotBinding.inflate(inflater, container, false)

        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),  TreeSpotActivity.TREESPOT_PERMISSIONS, cameraRequestCode)
        } else {
            setupCamera()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureDir = ActivityUtil.getAppImagesDirectoryAsFile(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                val preview = Preview.Builder()
                    .setTargetRotation(binding.root.display.rotation)
                    .build().also {
                        it.setSurfaceProvider(binding.captureSpotPreview.surfaceProvider)
                    }

                imageCapture = ImageCapture.Builder()
                    .setTargetRotation(binding.root.display.rotation)
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                    .build()

                preview.setSurfaceProvider(binding.captureSpotPreview.surfaceProvider)

                cameraProvider.unbindAll()
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment CaptureSpotFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CaptureSpotFragment().apply {
                arguments = Bundle().apply {

                }
            }

        const val BACKSTACK = "CAPTURE_SPOT_BACKSTACK"
    }
}