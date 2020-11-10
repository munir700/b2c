package co.yap.widgets.scanqrcode

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentScanQrCodeBinding
import co.yap.yapcore.helpers.extentions.getQRCode
import co.yap.yapcore.helpers.permissions.PermissionHelper
import com.dlazaro66.qrcodereaderview.QRCodeReaderView

class ScanQRCodeFragment : BaseBindingFragment<IScanQRCode.ViewModel>(),
    IScanQRCode.View, QRCodeReaderView.OnQRCodeReadListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_scan_qr_code
    lateinit var qrCodeReaderView: QRCodeReaderView
    var permissionHelper: PermissionHelper? = null

    override val viewModel: ScanQRCodeViewModel
        get() = ViewModelProviders.of(this).get(
            ScanQRCodeViewModel::class.java
        )

    private fun getBindings(): FragmentScanQrCodeBinding =
        viewDataBinding as FragmentScanQrCodeBinding

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        getBindings().tvQrCode.text = text
        qrCodeReaderView.setQRDecodingEnabled(false)
        viewModel.uploadQRCode(text?.getQRCode())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.contactInfo.observe(this, onFetchContactInfo)
    }

    private fun initQRCodeReaderView() {
        qrCodeReaderView = getBindings().qrCodeReaderView
        qrCodeReaderView.setAutofocusInterval(2000L)
        qrCodeReaderView.setOnQRCodeReadListener(this)
        qrCodeReaderView.setBackCamera()
        qrCodeReaderView.startCamera()
        qrCodeReaderView.setQRDecodingEnabled(true)
//      val reder =   QRCodeReader()
//        reder.decode()
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return false
    }

    override fun requestPermissions() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.CAMERA
            ), 101
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                initQRCodeReaderView()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                Toast.makeText(
                    context,
                    getString(R.string.common_permission_rejected_error),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onPermissionDenied() {

            }

            override fun onPermissionDeniedBySystem() {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        requestPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrCodeReaderView.stopCamera()
    }

    private val onFetchContactInfo = Observer<Contact> {
        it?.run {
            val intent = Intent()
            intent.putExtra("contact", it)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }
}