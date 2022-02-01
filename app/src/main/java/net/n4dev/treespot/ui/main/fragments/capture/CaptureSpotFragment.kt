package net.n4dev.treespot.ui.main.fragments.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import net.n4dev.treespot.TreeSpotCameraException
import net.n4dev.treespot.databinding.FragmentCaptureSpotBinding
import net.n4dev.treespot.util.ActivityUtil
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A fragment to take a picture from the user's TreeSpot.
 */
class CaptureSpotFragment : Fragment() {

    private var _binding : FragmentCaptureSpotBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() =  _binding!!
    private var facingLens : Int = CameraSelector.LENS_FACING_BACK
    private var imageCapture: ImageCapture? = null
    private var camera : Camera? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var pictureDir : File
    private var cameraProvider : ProcessCameraProvider? = null

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


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        pictureDir = ActivityUtil.getAppImagesDirectoryAsFile(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
         cameraProvider = cameraProviderFuture.get()

         facingLens = when {
             hasBackCamera() -> CameraSelector.LENS_FACING_BACK
             else -> throw TreeSpotCameraException("Only the back facing camera is supported!")
         }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
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
    }
}