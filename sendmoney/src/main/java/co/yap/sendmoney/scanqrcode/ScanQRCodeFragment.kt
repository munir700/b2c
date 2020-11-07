package co.yap.sendmoney.scanqrcode

import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.FragmentScanQrCodeBinding
import co.yap.sendmoney.fragments.SendMoneyBaseFragment
import com.dlazaro66.qrcodereaderview.QRCodeReaderView

class ScanQRCodeFragment : SendMoneyBaseFragment<IScanQRCode.ViewModel>(),
    IScanQRCode.View, QRCodeReaderView.OnQRCodeReadListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_scan_qr_code
    lateinit var qrCodeReaderView: QRCodeReaderView

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
        initQRCodeReaderView()
    }


    private fun initQRCodeReaderView() {
        val qrCodeReaderView = getBindings().qrCodeReaderView
        qrCodeReaderView.setAutofocusInterval(2000L)
        qrCodeReaderView.setOnQRCodeReadListener(this)
        qrCodeReaderView.setBackCamera()
        qrCodeReaderView.startCamera()
        qrCodeReaderView.setQRDecodingEnabled(true)
//      val reder =   QRCodeReader()
//        reder.decode()
    }
}