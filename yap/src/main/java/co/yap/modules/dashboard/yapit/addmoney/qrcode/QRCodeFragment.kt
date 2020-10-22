package co.yap.modules.dashboard.yapit.addmoney.qrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.helpers.Utils

class QRCodeFragment :DialogFragment(), IQRCode.View {
    lateinit var viewDataBinding: ViewDataBinding
    fun getBindingVariable(): Int = BR.viewModel
    fun getLayoutId(): Int = R.layout.fragment_qr_code

   override val viewModel: IQRCode.ViewModel
        get() = ViewModelProviders.of(this).get(QRCodeViewModel::class.java)

    override fun showLoader(isVisible: Boolean) {
    }

    override fun showToast(msg: String) {
    }

    override fun showInternetSnack(isVisible: Boolean) {
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return false
    }

    override fun requestPermissions() {
    }

    override fun getString(resourceKey: String): String {
        return ""
    }

    override fun onNetworkStateChanged(isConnected: Boolean) {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.myDialog)
        viewModel.onCreate()
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tvSaveToGallery -> {
                showToast("Save to Gallery")
            }
            R.id.tvShareMyCode -> {
                Utils.shareText(requireContext(), "QR Code")
            }
            R.id.ivBack -> {
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }
}