package co.yap.modules.others.helper

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
    @BindingAdapter(value = ["imageUrl", "fullName", "position", "colorType"], requireAll = false)
    fun loadAvatar(
        imageView: ImageView,
        imageUrl: String,
        fullName: String,
        position: Int,
        colorType: String
    ) {
        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._40sdp))
            .height(imageView.context.dimen(R.dimen._40sdp)).
                fontSize(imageView.context.dimen(R.dimen.text_size_h3))
            .useFont(ResourcesCompat.getFont(imageView.context , R.font.roboto_regular)!!)
            .textColor(getTextColorFromType(colorType, imageView, position))

        setCircleCropImage(
            imageView,
            imageUrl,
            builder.buildRect(
                Utils.shortName(fullName),
                getBgColorFromType(colorType, imageView, position)
            )
        )
    }

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "fullName", "position", "isBackground"],
        requireAll = false
    )
    fun loadAvatar1(
        imageView: ImageView,
        imageUrl: String,
        fullName: String,
        position: Int,
        isBackground: Boolean = true
    ) {
        if (fullName.isNullOrEmpty()) return

        val colors = imageView.context.resources.getIntArray(co.yap.yapcore.R.array.analyticsColors)
        val resId = getResId("ic_${getDrawableName(fullName)}")
        if (resId != -1) {
            val resImg = ContextCompat.getDrawable(imageView.context, resId)
            if (isBackground)
                resImg?.setTint(getAnalyticsColor(colors, position))
            else {
                resImg?.setTint(getAnalyticsColor(colors, position))
            }
            setCircleCropImage(imageView, imageUrl, resImg!!)

        } else {
            setDrawable(imageView, imageUrl, fullName, position)
        }
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


    private fun setDrawable(
        imageView: ImageView,
        imageUrl: String,
        fullName: String,
        position: Int
    ) {
        val colors = imageView.context.resources.getIntArray(co.yap.yapcore.R.array.analyticsColors)
        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._40sdp))
            .height(imageView.context.dimen(R.dimen._40sdp))
            .fontSize(imageView.context.dimen(R.dimen.text_size_h3))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!)
            .textColor(getAnalyticsColor(colors, position))
        setCircleCropImage(
            imageView,
            imageUrl,
            builder.buildRect(
                Utils.shortName(fullName),
                Utils.getBackgroundColor(imageView.context, position = position)
            )
        )
    }

    private fun getAnalyticsColor(colors: IntArray, position: Int): Int {
        return colors[position % colors.size]
    }

    private fun getDrawableName(title: String): String {
        return title.replace(" ", "_").toLowerCase()
    }

    private fun getResId(drawableName: String): Int {
        return try {
            val res = R.drawable::class.java
            val field = res.getField(drawableName)
            field.getInt(null)
        } catch (e: Exception) {
            -1
        }
    }

    private fun getTextColorFromType(colorType: String, imageView: ImageView, position: Int): Int {

       return when (colorType) {
            "Beneficiary" -> Utils.getBeneficiaryColors(imageView.context, position = position)
            else -> Utils.getContactColors(imageView.context, position = position)

        }
    }

    private fun getBgColorFromType(colorType: String, imageView: ImageView, position: Int): Int {
        return when (colorType) {
            "Beneficiary" -> Utils.getBeneficiaryBackgroundColor(imageView.context, position = position)
            else -> Utils.getBackgroundColor(imageView.context, position = position)

        }
    }
}