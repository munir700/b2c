package co.yap.modules.others.helper

import android.content.res.Resources
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import co.yap.R
import co.yap.widgets.TextDrawable
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.dimen
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
    @BindingAdapter(value = ["imageUrl", "fullName", "position"], requireAll = false)
    fun loadAvatar(imageView: ImageView, imageUrl: String, fullName: String, position: Int) {
        val builder = TextDrawable.builder();
        builder.beginConfig().width(imageView.context.dimen(R.dimen._40sdp))
            .height(imageView.context.dimen(R.dimen._40sdp)).
                fontSize(imageView.context.dimen(R.dimen.text_size_h3))
            .useFont(ResourcesCompat.getFont(imageView.context , R.font.roboto_regular)!!)
            .textColor(Utils.getContactColors(imageView.context, position = position))

        setCircleCropImage(
            imageView,
            imageUrl,
            builder.buildRect(
                Utils.shortName(fullName),
                Utils.getBackgroundColor(imageView.context, position = position)
            )
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