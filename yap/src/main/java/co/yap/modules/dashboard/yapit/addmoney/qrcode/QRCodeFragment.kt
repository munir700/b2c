package co.yap.modules.dashboard.yapit.addmoney.qrcode

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.permissions.PermissionHelper
import kotlinx.android.synthetic.main.fragment_qr_code.*
import java.io.File
import java.io.FileOutputStream


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

   private fun updateUI(){
       ImageBinding.loadAvatar(
           ivProfilePic,
           viewModel.state.profilePictureUrl,
           viewModel.state.fullName,
           android.R.color.transparent,
           R.dimen.text_size_h2
       )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.QRCodeTheme)
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

    private fun captureAndSaveQR() {
        val image = takeScreenshotForView(qrContainer)
        image?.let { storeBitmap(it, "qrcodetest") }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    fun takeScreenshotForView(view: View): Bitmap? {
        view.measure(
            MeasureSpec.makeMeasureSpec(view.width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(view.height, MeasureSpec.EXACTLY)
        )
        view.layout(
            view.x.toInt(),
            view.y.toInt(),
            view.x.toInt() + view.measuredWidth,
            view.y.toInt() + view.measuredHeight
        )
        var bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        view.draw(canvas)
        /*  return bitmap
          view.isDrawingCacheEnabled = true
          view.buildDrawingCache(true)
          val bitmap = Bitmap.createBitmap(view.drawingCache)
          view.isDrawingCacheEnabled = false*/
        showToast("Save to Gallery")
        return bitmap
    }

    fun storeBitmap(bitmap: Bitmap, image_name: String) {

        val root = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        ).toString()
        val myDir = File("$root/yap_saved_images")
        myDir.mkdirs()
        val fname = "Image-" + image_name + ".jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        MediaScannerConnection.scanFile(requireContext(), arrayOf(file.toString()), null,
            OnScanCompletedListener { path, uri ->
            })
    }

    private fun checkPermission() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                captureAndSaveQR()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDenied() {

            }

            override fun onPermissionDeniedBySystem() {

            }
        })
    }

}