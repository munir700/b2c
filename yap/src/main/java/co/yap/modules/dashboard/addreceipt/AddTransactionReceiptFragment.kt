package co.yap.modules.dashboard.addreceipt

import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddTransactionReceiptBinding
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.createTempFile
import com.digitify.identityscanner.camera.CameraException
import com.digitify.identityscanner.camera.CameraListener
import com.digitify.identityscanner.camera.CameraOptions
import com.digitify.identityscanner.camera.PictureResult

class AddTransactionReceiptFragment : BaseBindingFragment<IAddTransactionReceipt.ViewModel>(),
    IAddTransactionReceipt.View, CameraListener {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_add_transaction_receipt
    override val viewModel: IAddTransactionReceipt.ViewModel
        get() = ViewModelProviders.of(this).get(AddTransactionReceiptViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBindingView().camera.setLifecycleOwner(this)
        getBindingView().camera.setLifecycleOwner(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getBindingView().camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getBindingView() = (viewDataBinding as FragmentAddTransactionReceiptBinding)
    override fun onCaptureProcessCompleted(filename: String?) {

    }

    override fun onCameraOpened(options: CameraOptions) {
    }

    override fun onCameraClosed() {
    }

    override fun onCameraError(exception: CameraException) {
    }

    override fun onPictureTaken(result: PictureResult) {
        result.toFile(
            requireContext().createTempFile(".jpg")
        ) { }
    }

    override fun onOrientationChanged(orientation: Int) {
    }

    override fun onAutoFocusStart(point: PointF) {
    }

    override fun onAutoFocusEnd(successful: Boolean, point: PointF) {
    }

    override fun onZoomChanged(newValue: Float, bounds: FloatArray, fingers: Array<out PointF>?) {
    }

    override fun onExposureCorrectionChanged(
        newValue: Float,
        bounds: FloatArray,
        fingers: Array<out PointF>?
    ) {
    }
}
