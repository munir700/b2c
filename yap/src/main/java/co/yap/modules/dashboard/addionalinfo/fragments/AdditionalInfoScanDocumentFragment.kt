package co.yap.modules.dashboard.addionalinfo.fragments

import android.app.Activity
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAdditionalInfoScanDocumentBinding
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoScanDocument
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoScanDocumentViewModel
import com.digitify.identityscanner.camera.CameraException
import com.digitify.identityscanner.camera.CameraListener
import com.digitify.identityscanner.camera.CameraOptions
import com.digitify.identityscanner.camera.PictureResult
import com.digitify.identityscanner.utils.ImageUtils
import java.io.File

class AdditionalInfoScanDocumentFragment :
    AdditionalInfoBaseFragment<IAdditionalInfoScanDocument.ViewModel>(),
    IAdditionalInfoScanDocument.View, CameraListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_scan_document

    override val viewModel: IAdditionalInfoScanDocument.ViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoScanDocumentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCamera()
    }

    private fun setUpCamera() {
        getBindings().camera.setLifecycleOwner(this)
        getBindings().camera.addCameraListener(this)
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivClose -> {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun getBindings(): FragmentAdditionalInfoScanDocumentBinding {
        return viewDataBinding as FragmentAdditionalInfoScanDocumentBinding
    }

    override fun onCameraOpened(options: CameraOptions) {
        getBindings().btnScan.isEnabled = true
        getBindings().btnScan.setOnClickListener { v: View? -> capturePicture() }
    }

    override fun onCameraClosed() {
        getBindings().btnScan.setOnClickListener(null)
        getBindings().btnScan.isEnabled = false
    }

    override fun onCameraError(exception: CameraException) {
        showToast(exception.message.toString())
    }

    override fun onPictureTaken(result: PictureResult) {
        result.toFile(
            ImageUtils.getFilePrivately(activity) ?: File(result.toString())
        ) { file: File? -> showImage(file) }
    }

    private fun showImage(file: File?) {
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }

    override fun onOrientationChanged(orientation: Int) {}

    override fun onAutoFocusStart(point: PointF) {}

    override fun onAutoFocusEnd(successful: Boolean, point: PointF) {}

    override fun onZoomChanged(newValue: Float, bounds: FloatArray, fingers: Array<out PointF>?) {}

    override fun onExposureCorrectionChanged(
        newValue: Float,
        bounds: FloatArray,
        fingers: Array<out PointF>?
    ) {
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getBindings().camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun capturePicture() {
        if (getBindings().camera.isTakingPicture) return
        getBindings().camera.takePicture()
    }
}