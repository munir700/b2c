package co.yap.modules.dashboard.yapit.addmoney.qrcode

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseBindingFragment

class QRCodeFragment :BaseBindingFragment<IQRCode.ViewModel>(), IQRCode.View {
    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = 0

    override val viewModel: IQRCode.ViewModel
        get() = ViewModelProviders.of(this).get(QRCodeViewModel::class.java)
}