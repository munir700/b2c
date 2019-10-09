package co.yap.modules.dashboard.more.profile.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.constants.Constants
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileSettingsViewModel
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.managers.MyUserManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.layout_profile_picture.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProfileSettingsFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View,
    CardClickListener {
    val IMAGE_PATH = Environment
        .getExternalStorageDirectory().path + "/eidScan"

    private lateinit var updatePhotoBottomSheet: UpdatePhotoBottomSheet

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    private var imageUri: Uri? = null
    private val MAX_IMAGE_DIMENSION = 300


    private val FINAL_TAKE_PHOTO = 1
    private val FINAL_CHOOSE_PHOTO = 2

    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileSettingsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvPersonalDetailView -> {

                    val action =
                        ProfileSettingsFragmentDirections.actionProfileSettingsFragmentToPersonalDetailsFragment(
                            viewModel.showExpiredBadge
                        )
                    findNavController().navigate(action)
//                  findNavController().navigate(R.id.action_profileSettingsFragment_to_personalDetailsFragment)
                }

                R.id.tvPrivacyView -> {

                }

                R.id.tvChangePasscode -> {

                }

                R.id.tvTermsAndConditionView -> {

                }

                R.id.tvFollowOnInstagram -> {

                }

                R.id.tvFollowOnTwitter -> {

                }

                R.id.tvLikeUsOnFaceBook -> {

                }

                R.id.ivProfilePic -> {
                    // change profile picture
                }

                R.id.tvLogOut -> {

                    logoutAlert()

                }

                R.id.rlAddNewProfilePic -> {
                    updatePhotoBottomSheet = UpdatePhotoBottomSheet(this)
                    updatePhotoBottomSheet.show(this!!.fragmentManager!!, "")
                }

                viewModel.PROFILE_PICTURE_UPLOADED -> {
//                  Glide.with(activity!!)
//                      .load(viewModel.)
//                      .transforms(CenterCrop(), RoundedCorners(115))
//                      .into(ivProfilePic)

                }

            }
        })
    }

    override fun onClick(eventType: Int) {

        updatePhotoBottomSheet.dismiss()

        when (eventType) {
            Constants.EVENT_ADD_PHOTO -> {
//                takePicture()
                val checkSelfPermission = ContextCompat.checkSelfPermission(
                    activity!!,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                val checkSelfCameraPermission = ContextCompat.checkSelfPermission(
                    activity!!,
                    android.Manifest.permission.CAMERA
                )
                if (checkSelfPermission != PackageManager.PERMISSION_GRANTED && checkSelfCameraPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                        ),
                        FINAL_TAKE_PHOTO
                    )
                } else {
                    takePicture()
                }
            }

            Constants.EVENT_CHOOSE_PHOTO -> {
                // choose photo
                selectProfilePicture()

            }

            R.id.tvNotificationsView -> {
                showToast("start notifications")
            }
        }
    }

    fun openMediaContent() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, FINAL_CHOOSE_PHOTO)
    }

    private fun selectProfilePicture() {

//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(
//            Intent.createChooser(intent, "Select Picture"),
//            FINAL_CHOOSE_PHOTO
//        )

        val intent = Intent()
//        intent.setType("image/*");
        intent.type = "image/jpeg"

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Picture"
            ), FINAL_CHOOSE_PHOTO
        )
        //
//
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        intent.type = "image/*"
//        startActivityForResult(intent, FINAL_CHOOSE_PHOTO)
    }


    private fun takePicture() {

//        val file = createImageFile()
        val file = File(activity!!.applicationContext.externalCacheDir, "output_image.jpg")
        if (file.exists()) {
            file.delete()
        }
//        outputImage.createNewFile()
        imageUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                activity!!.applicationContext,
                "co.yap.fileprovider",
                file
            )
        } else {
            Uri.fromFile(file)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, FINAL_TAKE_PHOTO)
    }


    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            Log.e("", "getRealPathFromURI Exception : $e")
            return ""
        } finally {
            cursor?.close()
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "title", null)
        return Uri.parse(path)
    }


    fun getOrientation(context: Context, photoUri: Uri): Int {
        /* it's on the external media. */
        val cursor = context.contentResolver.query(
            photoUri,
            arrayOf(MediaStore.Images.ImageColumns.ORIENTATION), null, null, null
        )

        if (cursor!!.count != 1) {
            return -1
        }

        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @Throws(IOException::class)
    fun getCorrectlyOrientedImage(context: Context, photoUri: Uri): Bitmap? {
        var `is` = context.contentResolver.openInputStream(photoUri)
        val dbo = BitmapFactory.Options()
        dbo.inJustDecodeBounds = true
        BitmapFactory.decodeStream(`is`, null, dbo)
        `is`!!.close()

        val rotatedWidth: Int
        val rotatedHeight: Int
        val orientation = getOrientation(context, photoUri)

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight
            rotatedHeight = dbo.outWidth
        } else {
            rotatedWidth = dbo.outWidth
            rotatedHeight = dbo.outHeight
        }

        var srcBitmap: Bitmap?
        `is` = context.contentResolver.openInputStream(photoUri)
        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
            val widthRatio = rotatedWidth.toFloat() / MAX_IMAGE_DIMENSION.toFloat()
            val heightRatio = rotatedHeight.toFloat() / MAX_IMAGE_DIMENSION.toFloat()
            val maxRatio = Math.max(widthRatio, heightRatio)

            // Create the bitmap from file
            val options = BitmapFactory.Options()
            options.inSampleSize = maxRatio.toInt()
            srcBitmap = BitmapFactory.decodeStream(`is`, null, options)
        } else {
            srcBitmap = BitmapFactory.decodeStream(`is`)
        }
        assert(`is` != null)
        `is`!!.close()

        /*
         * if the orientation is not 0 (or -1, which means we don'retrofit know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            val matrix = Matrix()
            matrix.postRotate(orientation.toFloat())

            srcBitmap = Bitmap.createBitmap(
                srcBitmap!!, 0, 0, srcBitmap.width,
                srcBitmap.height, matrix, true
            )
        }

        return srcBitmap
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap? = null

        when (requestCode) {
            FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {

                    try {
                        imageUri = data!!.getData()
                        var selectedImage: Bitmap
                        if (imageUri == null) {
                            selectedImage = data!!.getExtras()!!.get("data") as Bitmap
                            selectedImage = data!!.data as Bitmap
                        } else {
                            selectedImage =
                                this!!.getCorrectlyOrientedImage(activity!!, imageUri!!)!!
                        }

                        val uri = getImageUri(activity!!, selectedImage)
                        val imgPath = getRealPathFromURI(activity!!, uri)
                        viewModel.uploadProfconvertUriToFile(Uri.parse(imgPath))

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {

                    val bitmap = BitmapFactory.decodeStream(
                        activity!!.getContentResolver().openInputStream(imageUri)
                    )
                    imageUri = getUri(bitmap)

                    if (imageUri != null) {

                        imageUri = Uri.parse(
                            viewModel.getRealPathFromUri(
                                this!!.context!!, imageUri!!
                            )
                        )
                        viewModel.uploadProfconvertUriToFile(imageUri!!)
                    }

                    Glide.with(activity!!)
                        .load(bitmap)
                        .transforms(CenterCrop(), RoundedCorners(115))
                        .into(ivProfilePic)
                }
        }
    }


    fun getUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)

        val path = MediaStore.Images.Media.insertImage(
            context!!.contentResolver,
            bitmap,
            Date().time.toString() + "_Title",
            null
        )
        return Uri.parse(path)
    }

    fun getBitmap(selectedimg: Uri?): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = 3
        var fileDescriptor: AssetFileDescriptor? = null
        fileDescriptor = activity!!.getContentResolver().openAssetFileDescriptor(selectedimg!!, "r")
        return BitmapFactory.decodeFileDescriptor(
            fileDescriptor!!.fileDescriptor, null, options
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

    fun logoutAlert() {
        AlertDialog.Builder(this!!.activity!!)
            .setTitle(getString(R.string.screen_profile_settings_logout_display_text_alert_title))
            .setMessage(getString(R.string.screen_profile_settings_logout_display_text_alert_message))
            .setPositiveButton(getString(R.string.screen_profile_settings_logout_display_text_alert_logout),
                DialogInterface.OnClickListener { dialog, which ->
                    doLogout()
                })

            .setNegativeButton(
                getString(R.string.screen_profile_settings_logout_display_text_alert_cancel),
                null
            )
            .show()
    }

    private fun doLogout() {
        AuthUtils.navigateToHardLogin(requireContext())
        MyUserManager.cardBalance.value = CardBalance()
        MyUserManager.cards.value?.clear()
        activity?.finish()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            FINAL_TAKE_PHOTO ->

                if (grantResults.isNotEmpty() && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                    takePicture()
                    Log.i("pictureLog", FINAL_TAKE_PHOTO.toString())

                }

            FINAL_CHOOSE_PHOTO ->

                if (grantResults.isNotEmpty() && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                    selectProfilePicture()
                    Log.i("pictureLog", FINAL_CHOOSE_PHOTO.toString())

                }
        }
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }


    private fun createImageFile(): File {
//        clearTempImages()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val file = File(
            IMAGE_PATH, "IMG_" + timeStamp +
                    ".jpg"
        )
        imageUri = Uri.fromFile(file)
        return file
    }


    private fun clearTempImages() {
        try {
            val tempFolder = File(IMAGE_PATH)
            for (f in tempFolder.listFiles())
                f.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}