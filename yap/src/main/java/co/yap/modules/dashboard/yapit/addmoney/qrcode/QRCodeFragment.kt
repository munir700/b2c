package co.yap.modules.dashboard.yapit.addmoney.qrcode

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.UserManager
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
import co.yap.modules.qrcode.BarcodeEncoder
import co.yap.modules.qrcode.BarcodeFormat
import co.yap.translation.Strings
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.storeBitmap
import co.yap.yapcore.helpers.permissions.PermissionHelper
import kotlinx.android.synthetic.main.fragment_qr_code.*

class QRCodeFragment : DialogFragment(), IQRCode.View {
    lateinit var viewDataBinding: ViewDataBinding
    fun getBindingVariable(): Int = BR.viewModel
    fun getLayoutId(): Int = R.layout.fragment_qr_code
    var permissionHelper: PermissionHelper? = null

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
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewDataBinding.executePendingBindings()
        updateUI()
    }

    private fun updateUI() {
        ImageBinding.loadAvatar(
            ivProfilePic,
            viewModel.state.profilePictureUrl,
            viewModel.state.fullName,
            android.R.color.transparent,
            R.dimen.text_size_h2
        )
        viewModel.state.qrBitmap = viewModel.qrUUID?.let { generateQrCode(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.QRCodeTheme)
        viewModel.onCreate()
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tvSaveToGallery -> {
                checkPermission()
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

    private fun checkPermission() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                storeBitmap(qrContainer, requireContext())

            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                showToast(getString(Strings.common_permission_rejected_error))

            }

            override fun onPermissionDenied() {

            }

            override fun onPermissionDeniedBySystem() {

            }
        })
    }

    private fun generateQrCode(resourceKey: String): Drawable? {
        var drawable: Drawable? = null
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap =
                barcodeEncoder.encodeBitmap(resourceKey, BarcodeFormat.QR_CODE, 400, 400)
            drawable = BitmapDrawable(resources, bitmap)
            return drawable
        } catch (e: Exception) {
        }
        return drawable
    }
}
