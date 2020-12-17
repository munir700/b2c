package co.yap.widgets.scanqrcode

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Strings
import co.yap.widgets.qrcode.QRCodeFragment
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.RequestCodes.REQUEST_CAMERA_PERMISSION
import co.yap.yapcore.databinding.FragmentScanQrCodeBinding
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.extentions.getQRCode
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.managers.SessionManager
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream

class ScanQRCodeFragment : BaseBindingFragment<IScanQRCode.ViewModel>(),
    IScanQRCode.View, QRCodeReaderView.OnQRCodeReadListener, EasyPermissions.PermissionCallbacks {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_scan_qr_code
    val cameraPer = 1
    var oneTimeCall = true;
    var qrCodeReaderView: QRCodeReaderView? = null
    var permissionHelper: PermissionHelper? = null
    var easyImage: EasyImage? = null

    override val viewModel: ScanQRCodeViewModel
        get() = ViewModelProviders.of(this).get(
            ScanQRCodeViewModel::class.java
        )

    private fun getBindings(): FragmentScanQrCodeBinding =
        viewDataBinding as FragmentScanQrCodeBinding

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        if (!viewModel.state.loading) {
            qrCodeReaderView?.setQRDecodingEnabled(false)
            sendQrRequest(text?.getQRCode())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.contactInfo.observe(this, onFetchContactInfo)
        viewModel.noContactFoundEvent.observe(this, onNoContactInfo)
    }

    override fun onResume() {
        super.onResume()
        checkPermission(cameraPer)
    }

    private fun initQRCodeReaderView() {
        qrCodeReaderView = getBindings().qrCodeReaderView
        qrCodeReaderView?.setAutofocusInterval(2000L)
        qrCodeReaderView?.setOnQRCodeReadListener(this)
        qrCodeReaderView?.setBackCamera()
        qrCodeReaderView?.startCamera()
        qrCodeReaderView?.setQRDecodingEnabled(true)
    }

    override fun onPause() {
        super.onPause()
        qrCodeReaderView?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrCodeReaderView?.stopCamera()
    }

    private val onFetchContactInfo = Observer<Beneficiary> {
        it?.let {
            val intent = Intent()
            intent.putExtra(Beneficiary::class.java.name, it)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }

    private val onNoContactInfo = Observer<Boolean> {
        qrCodeReaderView?.setQRDecodingEnabled(true)
    }

    private fun scanQRImage(bMap: Bitmap): String? {
        var contents: String? = null
        val intArray = IntArray(bMap.width * bMap.height)
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
            showToast("Error decoding QRCode")
            qrCodeReaderView?.setQRDecodingEnabled(true)
        }
        return contents
    }

    private fun checkPermission(type: Int) {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), REQUEST_CAMERA_PERMISSION
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                if (type == cameraPer) {
                    initQRCodeReaderView()
                } else {
                    initEasyImage()
                }
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                if (type == cameraPer) {
                    if (grantedPermission.contains(Manifest.permission.CAMERA))
                        initQRCodeReaderView()
                } else {
                    if (grantedPermission.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) initEasyImage()
                }
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")
                qrCodeReaderView?.setQRDecodingEnabled(true)
            }

            override fun onPermissionDeniedBySystem() {
                showToast("Can't proceed without permissions")
                qrCodeReaderView?.setQRDecodingEnabled(true)

            }
        })
    }

    private fun initEasyImage() {
        if (hasCameraPermission()) {
            easyImage = EasyImage.Builder(requireContext())
                .setChooserTitle("Pick Image")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("YAPImage")
                .allowMultiple(false)
                .build()
            easyImage?.openGallery(this)
        } else {
            EasyPermissions.requestPermissions(
                this, "This app needs access to your camera so you can take pictures.",
                REQUEST_CAMERA_PERMISSION, Manifest.permission.CAMERA
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            handleImagePickerResult(requestCode, resultCode, data)
        }

    }

    private fun handleImagePickerResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        easyImage?.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(
                    imageFiles: Array<MediaFile>,
                    source: MediaSource
                ) {
                    onPhotosReturned(imageFiles, source)
                }

                override fun onImagePickerError(
                    @NonNull error: Throwable,
                    @NonNull source: MediaSource
                ) {
                    qrCodeReaderView?.setQRDecodingEnabled(true)
                    viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                    error.printStackTrace()
                }

                override fun onCanceled(@NonNull source: MediaSource) {
                    qrCodeReaderView?.setQRDecodingEnabled(true)
                    viewModel.state.toast = "No Image Selected^${AlertType.DIALOG.name}"
                }
            })
    }

    private fun onPhotosReturned(path: Array<MediaFile>, source: MediaSource) {
        path.firstOrNull()?.let { mediaFile ->
            val ext = mediaFile.file.extension
            if (!ext.isBlank()) {
                when (ext) {
                    "png", "jpg", "jpeg" -> {
                        val inputStream: InputStream =
                            BufferedInputStream(FileInputStream(mediaFile.file))
                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                        scanQRImage(bitmap)?.let {
                            sendQrRequest(it.getQRCode())
                        }
                    }
                    else -> {
                        viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                        qrCodeReaderView?.setQRDecodingEnabled(true)
                    }

                }
            } else {
                viewModel.state.toast = "Invalid file found^${AlertType.DIALOG.name}"
                qrCodeReaderView?.setQRDecodingEnabled(true)
            }
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivBack -> {
                requireActivity().onBackPressed()
            }
            R.id.ivLibrary -> {
                qrCodeReaderView?.setQRDecodingEnabled(false)
                checkPermission(2)
            }
            R.id.ivMyQrCode -> {
                QRCodeFragment {
                    qrCodeReaderView?.setQRDecodingEnabled(true)
                }.let { fragment ->
                    if (isAdded)
                        qrCodeReaderView?.setQRDecodingEnabled(false)
                    fragment.show(requireActivity().supportFragmentManager, "")
                }
            }
        }
    }

    private fun sendQrRequest(qrCode: String?) {
        SessionManager.user?.let { accountInfo ->
            if (qrCode == accountInfo.encryptedAccountUUID) {
                showToast(getString(Strings.screen_qr_code_own_uuid_error_message))
            } else {
                viewModel.uploadQRCode(qrCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.CAMERA)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}