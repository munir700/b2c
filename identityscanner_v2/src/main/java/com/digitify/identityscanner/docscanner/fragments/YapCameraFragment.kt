package com.digitify.identityscanner.docscanner.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.translation.Strings
import co.yap.translation.Translator.getString
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.rx.Task
import com.digitify.identityscanner.BR
import com.digitify.identityscanner.R
import com.digitify.identityscanner.base.BaseFragment
import com.digitify.identityscanner.camera.CameraException
import com.digitify.identityscanner.camera.CameraListener
import com.digitify.identityscanner.camera.CameraOptions
import com.digitify.identityscanner.camera.PictureResult
import com.digitify.identityscanner.components.Overlay
import com.digitify.identityscanner.components.TransparentCardView
import com.digitify.identityscanner.databinding.FragmentCameraBinding
import com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.docscanner.enums.DocumentPageType
import com.digitify.identityscanner.docscanner.enums.DocumentType
import com.digitify.identityscanner.docscanner.interfaces.Cropper
import com.digitify.identityscanner.docscanner.interfaces.ICamera
import com.digitify.identityscanner.docscanner.viewmodels.CameraViewModel
import com.digitify.identityscanner.docscanner.viewmodels.IdentityScannerViewModel
import com.digitify.identityscanner.utils.ImageUtils
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.text.TextRecognition
import id.zelory.compressor.overWrite
import java.io.File
import java.io.IOException

class YapCameraFragment : BaseFragment(),
    ICamera.View, CameraListener {
    private var overlay: Overlay? = null
    private var progress: Dialog? = null

    //    private var cardOverlay: TransparentCardView? = null
    private var viewModel: CameraViewModel? = null
    private var parentViewModel: IdentityScannerViewModel? = null
    private lateinit var binding: FragmentCameraBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        parentViewModel = ViewModelProviders.of(requireActivity()).get(
            IdentityScannerViewModel::class.java
        )
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        val view = binding.root
        binding.model = viewModel
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        viewModel?.onStart()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.camera.setLifecycleOwner(this)
        binding.camera.addCameraListener(this)
        if (progress == null)
            progress = Utils.createProgressDialog(requireActivity())
    }

    override fun getScreenTitle(): String {
        return getString(R.string.activity_scan_title)
    }

    private fun init() {
        overlay = binding.drawView //,view!!.findViewById(R.id.drawView)
//        cardOverlay = view!!.findViewById(R.id.cardOverlay)
        binding.cardOverlay.cardRatio =
            if (parentViewModel?.documentType == DocumentType.PASSPORT) TransparentCardView.PASSPORT_RATIO else TransparentCardView.ID_CARD_RATIO
        viewModel?.documentType = parentViewModel?.documentType
        viewModel?.scanMode = parentViewModel?.state?.scanMode
    }

    private fun capturePicture() {
        if (binding.camera.isTakingPicture) return
        viewModel?.state?.isCapturing = false
        binding.camera.takePictureSnapshot()
    }

    private val propertyChangedCallback: OnPropertyChangedCallback =
        object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                sender: Observable,
                propertyId: Int
            ) {
                if (propertyId == BR.scanMode) {
                    viewModel?.scanMode = parentViewModel?.state?.scanMode
                }

//            else if (propertyId == com.digitify.identityscanner.BR.cardRect) {
////                updateDrawView(getViewModel().getState().getCardRect());
//            }
            }
        }

    override fun openCropper(filename: String) {
        if (!TextUtils.isEmpty(filename)) {
            if (parentViewModel?.state?.scanMode == DocumentPageType.BACK) test(filename = filename) else onCaptureProcessCompleted(
                filename
            )

        } else {
            setInstructions(
                getString(
                    requireContext(),
                    Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_saving_file
                )
            )
        }
    }

    private val cropper: Cropper = object : Cropper() {
        override fun onCropped(uri: Uri) {
            IdentityScannerActivity.imageFiles.add(uri.path)
            onCaptureProcessCompleted(uri.path!!)
        }

        override fun onCropFailed(e: Exception) {
            setInstructions(e.localizedMessage)
        }
    }

    override fun onCaptureProcessCompleted(filename: String) {
        setInstructions(
            getString(
                requireContext(),
                Strings.idenetity_scanner_sdk_screen_review_info_display_text_capture_process_complete
            )
        )
        parentViewModel?.onPictureTaken(filename)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        cropper.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        viewModel?.capturedImage?.observe(
            this,
            Observer { filename: String ->
                openCropper(
                    filename
                )
            }
        )
        viewModel?.capturedDocument?.observe(
            this,
            Observer { filename: String ->
                onCaptureProcessCompleted(
                    filename
                )
            }
        )
        viewModel?.state?.addOnPropertyChangedCallback(propertyChangedCallback)
        parentViewModel?.state?.addOnPropertyChangedCallback(propertyChangedCallback)
    }

    override fun onPause() {
        viewModel?.onStop()
        viewModel?.state?.removeOnPropertyChangedCallback(propertyChangedCallback)
        parentViewModel?.state?.removeOnPropertyChangedCallback(propertyChangedCallback)
        super.onPause()
    }

    override fun setInstructions(inst: String) {
        viewModel?.setInstructions(inst)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        binding.camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCameraOpened(options: CameraOptions) {
        viewModel?.state?.isCapturing = true
        binding.camFab.isEnabled = true
        binding.camFab.setOnClickListener { v: View? -> capturePicture() }
    }

    override fun onCameraClosed() {
        binding.camFab.setOnClickListener(null)
        binding.camFab.isEnabled = false
        viewModel?.state?.isCapturing = false
    }

    override fun onCameraError(exception: CameraException) {
        viewModel?.state?.isCapturing = false
        showToast(exception.message)
    }

    override fun onPictureTaken(result: PictureResult) {
        result.toFile(
            ImageUtils.getFilePrivately(requireContext())!!
        ) { file: File? -> viewModel?.handleOnPressCapture(file) }
    }

    override fun onOrientationChanged(orientation: Int) {}
    override fun onAutoFocusStart(point: PointF) {}
    override fun onAutoFocusEnd(successful: Boolean, point: PointF) {}
    override fun onZoomChanged(
        newValue: Float,
        bounds: FloatArray,
        fingers: Array<PointF>?
    ) {
    }

    override fun onExposureCorrectionChanged(
        newValue: Float,
        bounds: FloatArray,
        fingers: Array<PointF>?
    ) {
    }

    private fun test(filename: String) {
        progress?.show()
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableClassification()  // Optional
            .build()
        val objectDetector = ObjectDetection.getClient(options)
        val image: InputImage
        try {
            image = InputImage.fromFilePath(requireContext(), Uri.fromFile(File(filename)))
            objectDetector.process(image)
                .addOnSuccessListener { detectedObjects ->
                    // Task completed successfully
                    if (detectedObjects.isEmpty()) {
                        progress?.hide()
                        toast("Please rescan the image")
                    } else {
                        for (detectedObject: DetectedObject in detectedObjects) {
                            val boundingBox: Rect = detectedObject.boundingBox
                            val sourceBitmap: Bitmap = BitmapFactory.decodeFile(filename)
                            val croppedBmp: Bitmap = Bitmap.createBitmap(
                                sourceBitmap,
                                (boundingBox.exactCenterX() - boundingBox.width() / 2).toInt(),
                                (boundingBox.exactCenterY() - boundingBox.height() / 2).toInt(),
                                boundingBox.width(),
                                boundingBox.height()
                            )
                            extractText(image) { lines ->
                                if (lines in 1..2) {
                                    Task.runSafely({
                                        overWrite(File(filename), croppedBmp)
                                    }, {
                                        progress?.hide()
                                        onCaptureProcessCompleted(filename)
                                    }, true)
                                } else {
                                    progress?.hide()
                                    showToast("Please rescan the image")
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    progress?.hide()
                    toast("Please rescan the image")
                }
        } catch (e: IOException) {
            e.printStackTrace()
            progress?.hide()
        }


    }


    private fun extractText(inputImage: InputImage, callback: (Int) -> Unit) {
        var lines: Int = 0
        val recognizer = TextRecognition.getClient()
        val line1 = "([A-Z]{4})([A-Z0-9<]{26})";
        val line2 =
            "^[A-Z0-9<]{9}[0-9]{1}[A-Z<]{3}[0-9]{6}[0-9]{1}[FM<]{1}[0-9]{6}[0-9]{1}[A-Z0-9<]{14}[0-9]{1}[0-9]{1}$^";
        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                for (block in visionText.textBlocks) {
                    for (line in block.lines) {
                        val lineText = line.text
                        if (lineText.contains(line1.toRegex()) || lineText.contains(
                                line2.toRegex()
                            )
                        ) {
                            lines += 1
                        }
                    }
                }
                callback(lines)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                toast("Please rescan the image")
            }
    }

    override fun onDestroyView() {
        progress?.dismiss()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        progress=null
    }
}