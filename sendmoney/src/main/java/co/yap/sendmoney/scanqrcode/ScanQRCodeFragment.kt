package co.yap.sendmoney.scanqrcode

import android.Manifest
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentScanQrCodeBinding
import co.yap.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.yapcore.helpers.permissions.PermissionHelper
import com.dlazaro66.qrcodereaderview.QRCodeReaderView

class ScanQRCodeFragment : SendMoneyBaseFragment<IScanQRCode.ViewModel>(),
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

}