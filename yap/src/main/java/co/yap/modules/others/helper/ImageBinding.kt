package co.yap.modules.others.helper

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import co.yap.widgets.TextDrawable

import co.yap.yapcore.helpers.Utils

import com.homemedics.app.glide.setCircleCropImage
import com.homemedics.app.glide.setImage


object ImageBinding {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: AppCompatImageView, url: String) {
        setImage(imageView, url)
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: AppCompatImageView, url: String) {
        setCircleCropImage(imageView, url)
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: AppCompatImageView, uri: Uri) {
        setImage(imageView, uri)
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "fullName"], requireAll = false)
    fun loadAvatar(imageView: ImageView, imageUrl: String, fullName: String) {
        val builder = TextDrawable.builder();
        builder.beginConfig().width(45).height(45)
        setCircleCropImage(
            imageView,
            imageUrl,
            builder.buildRect(Utils.shortName(fullName), Color.RED)
        )
    }


    @JvmStatic
    @BindingAdapter("imageUrl", "app:srcCompat")
    fun setNavigationViewImageUrl(imageView: AppCompatImageView, url: String, resource: Int) {

        if (resource > 0) imageView.setImageResource(resource) else setImage(imageView, url)
    }

    @JvmStatic
    @BindingAdapter("app:srcCompat")
    fun setImageViewResource(imageView: AppCompatImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

}