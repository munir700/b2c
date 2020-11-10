package co.yap.widgets.scanqrcode

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentScanQrCodeBinding
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.extentions.getQRCode
import co.yap.yapcore.helpers.permissions.PermissionHelper
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class ScanQRCodeFragment : BaseBindingFragment<IScanQRCode.ViewModel>(),
    IScanQRCode.View, QRCodeReaderView.OnQRCodeReadListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_scan_qr_code
    val cameraPer = 1
    var oneTimeCall = true;
    lateinit var qrCodeReaderView: QRCodeReaderView
    var permissionHelper: PermissionHelper? = null

    override val viewModel: ScanQRCodeViewModel
        get() = ViewModelProviders.of(this).get(
            ScanQRCodeViewModel::class.java
        )

    private fun getBindings(): FragmentScanQrCodeBinding =
        viewDataBinding as FragmentScanQrCodeBinding

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        if (!viewModel.state.loading) {
            qrCodeReaderView.setQRDecodingEnabled(false)
            viewModel.uploadQRCode(text?.getQRCode())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.contactInfo.observe(this, onFetchContactInfo)
        viewModel.noContactFoundEvent.observe(this, onNoContactInfo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission(cameraPer)
    }

    private fun initQRCodeReaderView() {
        qrCodeReaderView = getBindings().qrCodeReaderView
        qrCodeReaderView.setAutofocusInterval(2000L)
        qrCodeReaderView.setOnQRCodeReadListener(this)
        qrCodeReaderView.setBackCamera()
        qrCodeReaderView.startCamera()
        qrCodeReaderView.setQRDecodingEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        qrCodeReaderView.stopCamera()
    }

    private val onFetchContactInfo = Observer<Contact> {
        it?.let {
            val intent = Intent()
            intent.putExtra("contact", it)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }

    private val onNoContactInfo = Observer<Boolean> {
        qrCodeReaderView.setQRDecodingEnabled(true)
    }

    private fun scanQRImage(bMap: Bitmap): String? {
        var contents: String? = null
        val intArray = IntArray(bMap.width * bMap.getHeight())
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.width, 0, 0, bMap.width, bMap.height)
        val source: LuminanceSource =
            RGBLuminanceSource(bMap.width, bMap.height, intArray)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        val reader: Reader = MultiFormatReader()
        try {
            val result: Result = reader.decode(bitmap)
            contents = result.text
        } catch (e: Exception) {
//            Log.e("QrTest", "Error decoding barcode", e)
            showToast("Error decoding QRCode")
            qrCodeReaderView.setQRDecodingEnabled(true)
        }
        return contents
    }

    private fun checkPermission(type: Int) {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                if (type == cameraPer) {
                    initQRCodeReaderView()
                } else {
                    EasyImage.openGallery(this@ScanQRCodeFragment, 2)
                }
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                if (type == cameraPer) {
                    if (grantedPermission.contains(Manifest.permission.CAMERA))
                        initQRCodeReaderView()
                } else {
                    if (grantedPermission.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        EasyImage.openGallery(this@ScanQRCodeFragment, 2)
                }
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")
                qrCodeReaderView.setQRDecodingEnabled(true)
            }

            override fun onPermissionDeniedBySystem() {
                showToast("Can't proceed without permissions")
                qrCodeReaderView.setQRDecodingEnabled(true)

//                permissionHelper?.openAppDetailsActivity()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, activity,
            object : DefaultCallback() {
                override fun onImagePicked(
                    imageFile: File?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    onPhotosReturned(imageFile, type, source)
                }

                override fun onImagePickerError(
                    e: Exception?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    qrCodeReaderView.setQRDecodingEnabled(true)
                    viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                }
            })
    }

    private fun onPhotosReturned(path: File?, position: Int, source: EasyImage.ImageSource?) {
        path?.let {
            val ext = path.extension
            if (!ext.isBlank()) {
                when (ext) {
                    "png", "jpg", "jpeg" -> {
                        val inputStream: InputStream = BufferedInputStream(FileInputStream(path))
                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                        scanQRImage(bitmap)?.let {
                            viewModel.uploadQRCode(it.getQRCode())
                        }
                    }
                    else -> {
                        viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                        qrCodeReaderView.setQRDecodingEnabled(true)
                    }
                }
            } else {
                viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                qrCodeReaderView.setQRDecodingEnabled(true)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivBack -> {
                requireActivity().onBackPressed()
            }
            R.id.ivLibrary -> {
                qrCodeReaderView.setQRDecodingEnabled(false)
                checkPermission(2)
            }
            R.id.ivMyQrCode -> {
            }
        }
    }
}