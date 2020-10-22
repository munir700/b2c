package co.yap.modules.dashboard.yapit.addmoney.qrcode

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.yapcore.BaseBindingFragment

class QRCodeFragment :BaseBindingFragment<IQRCode.ViewModel>(), IQRCode.View {
    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.fragment_qr_code

    override val viewModel: IQRCode.ViewModel
        get() = ViewModelProviders.of(this).get(QRCodeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}