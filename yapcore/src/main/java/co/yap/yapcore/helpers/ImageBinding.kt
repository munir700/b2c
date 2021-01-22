package co.yap.yapcore.helpers

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import co.yap.widgets.CoreCircularImageView
import co.yap.widgets.PrefixSuffixEditText
import co.yap.widgets.TextDrawable
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.getMerchantCategoryIcon
import co.yap.yapcore.helpers.extentions.loadCardImage
import co.yap.yapcore.helpers.glide.setCircleCropImage
import co.yap.yapcore.helpers.glide.setImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import co.yap.yapcore.helpers.glide.setRoundedImage
import com.google.android.material.floatingactionbutton.FloatingActionButton

object ImageBinding {
    @JvmStatic
    @BindingAdapter(
        value = ["roundedImageUrl", "radius"],
        requireAll = true
    )
    fun setImageUrl(
        imageView: AppCompatImageView,
        imageUrl: String?, radius: Int
    ) {
        imageUrl?.let {
            setRoundedImage(imageView, it, radius)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "fullName", "bgColor", "initialTextSize", "initialTextColor"],
        requireAll = true
    )
    fun setImageUrl(
        imageView: AppCompatImageView,
        imageUrl: String?,
        fullName: String,
        bgColor: Int, initialTextSize: Int,
        initialTextColor: Int
    ) {
        loadAvatar(
            imageView,
            imageUrl,
            fullName,
            bgColor,
            initialTextSize,
            initialTextColor
        )
    }

    @JvmStatic
    @BindingAdapter("cardImageUrl")
    fun setCardImageUrl(imageView: AppCompatImageView, url: String) {
        imageView.loadCardImage(url)
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: AppCompatImageView, url: String?) {
        url?.let { setCircleCropImage(imageView, it) }
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: AppCompatImageView, uri: Uri) {
        setImage(imageView, uri)
    }

    @JvmStatic
    @BindingAdapter(value = ["beneficiaryPicture", "fullName"], requireAll = true)
    fun loadAvatar(imageView: ImageView, beneficiaryPicture: String?, fullName: String?) {
        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._40sdp))
            .height(imageView.context.dimen(R.dimen._40sdp))
            .fontSize(imageView.context.dimen(R.dimen.text_size_h3))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!)
            .textColor(ThemeColorUtils.colorCircularTextAttribute(imageView.context))
        setCircleCropImage(
            imageView,
            beneficiaryPicture ?: "",
            builder.buildRect(
                Utils.shortName(fullName ?: ""),
                ThemeColorUtils.colorDisabledLightAttribute(imageView.context)
            )
        )
    }

    fun loadAvatar(
        imageView: ImageView,
        beneficiaryPicture: String?,
        fullName: String?,
        @ColorRes color: Int,
        @DimenRes fontSize: Int = R.dimen.text_size_h5,
        @ColorRes textColor: Int = R.color.colorPrimary
    ) {
        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._35sdp))
            .height(imageView.context.dimen(R.dimen._35sdp))
            .fontSize(imageView.context.dimen(fontSize))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!).bold()
            .toUpperCase()
            .textColor(ContextCompat.getColor(imageView.context, textColor))
        setCircleCropImage(
            imageView,
            beneficiaryPicture ?: "",
            builder.buildRect(
                Utils.shortName(fullName ?: ""),
                ContextCompat.getColor(imageView.context, color)
            )
        )
    }

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "fullName", "drawableName"],
        requireAll = true
    )
    fun loadAvatarOrResDrawable(
        imageView: ImageView,
        imageUrl: String?,
        fullName: String?,
        drawableName: String?
    ) {
        drawableName?.let {
            imageView.setImageResource(getResId(drawableName))
        } ?: loadAvatar(
            imageView,
            imageUrl,
            fullName,
            R.color.disabledLight,
            R.dimen.text_size_h2,
            R.color.colorPrimary
        )
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "fullName", "position", "colorType"], requireAll = false)
    fun loadAvatar(
        imageView: ImageView,
        imageUrl: String?,
        fullName: String?,
        position: Int,
        colorType: String = ""
    ) {
        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._40sdp))
            .height(imageView.context.dimen(R.dimen._40sdp))
            .fontSize(imageView.context.dimen(R.dimen.text_size_h3))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!)
            .textColor(
                getTextColorFromType(
                    colorType,
                    imageView,
                    position
                )
            )
        setCircleCropImage(
            imageView,
            imageUrl ?: "",
            builder.buildRect(
                Utils.shortName(fullName ?: ""),
                getBgColorFromType(
                    colorType,
                    imageView,
                    position
                )
            )
        )
    }

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "fullName", "position", "isBackground", "showFirstInitials"],
        requireAll = false
    )
    fun loadAnalyticsAvatar(
        imageView: ImageView,
        imageUrl: String?,
        fullName: String?,
        position: Int,
        isBackground: Boolean = true,
        showFirstInitials: Boolean = false
    ) {
        if (fullName.isNullOrEmpty()) return
        val fName = fullName ?: ""

        val colors = imageView.context.resources.getIntArray(R.array.analyticsColors)
        val resId =
            if (isBackground) getResId("ic_${getDrawableName(fName)}") else fName.getMerchantCategoryIcon()

        if (resId != -1) {
            val resImg = ContextCompat.getDrawable(imageView.context, resId)
            if (isBackground)
                resImg?.setTint(
                    getAnalyticsColor(
                        colors,
                        position
                    )
                )
            else {
                resImg?.setTint(
                    getAnalyticsColor(
                        colors,
                        position
                    )
                )
            }
            setCircleCropImage(imageView, imageUrl ?: "", resImg!!)
        } else {
            setDrawable(
                imageView,
                imageUrl,
                if (showFirstInitials) fName.split(" ")[0] else fName,
                position
            )
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
    @JvmStatic
    @BindingAdapter("app:srcCompat")
    fun setFloatingActionButtonResource(imageView: FloatingActionButton, resource: Int) {
        imageView.setImageResource(resource)
    }
    @JvmStatic
    @BindingAdapter("app:srcCompatGif")
    fun setGifImageViewResource(imageView: AppCompatImageView, resource: Int) {
        Glide.with(imageView.context).asGif().load(resource)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.setLoopCount(1)
                    return false
                }
            }).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:srcCompat")
    fun setImageViewResource(imageView: CoreCircularImageView, resource: Int) {
        imageView.setImageResource(resource)
    }


    @JvmStatic
    @BindingAdapter("app:setFlagDrawable")
    fun setIsoCountryDrawable(imageView: ImageView, isoCountryCode: String?) {
        isoCountryCode?.let {
            val resId = getResId(
                "flag_${getDrawableName(it)}"
            )
            if (resId != -1) {
                imageView.setImageResource(resId)
            }
        }
    }

    private fun setDrawable(
        imageView: ImageView,
        imageUrl: String?,
        fullName: String?,
        position: Int
    ) {
        val colors = imageView.context.resources.getIntArray(R.array.analyticsColors)
        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._40sdp))
            .height(imageView.context.dimen(R.dimen._40sdp))
            .fontSize(imageView.context.dimen(R.dimen.text_size_h3))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!)
            .textColor(
                getAnalyticsColor(
                    colors,
                    position
                )
            )

        setCircleCropImage(
            imageView,
            imageUrl ?: "",
            builder.buildRect(
                Utils.shortName(fullName ?: ""),
                Utils.getBackgroundColorForAnalytics(imageView.context, position = position)
            )
        )
    }

    private fun getAnalyticsColor(colors: IntArray, position: Int): Int {
        return colors[position % colors.size]
    }

    fun getDrawableName(title: String): String {
        return title.replace(" ", "_").toLowerCase()
    }

    fun getResId(drawableName: String): Int {
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
            "Beneficiary" -> Utils.getBeneficiaryBackgroundColor(
                imageView.context,
                position = position
            )
            else -> Utils.getBackgroundColor(imageView.context, position = position)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["countryCode", "countryName"], requireAll = false)
    fun setPhonePrefix(view: PrefixSuffixEditText, countryCode: String, countryName: String) {
        val resId = getResId(
            "flag_${
            getDrawableName(
                countryName
            )
            }"
        )
        if (resId != -1) {
            view.prefixDrawable = ContextCompat.getDrawable(view.context, resId)
        }
        view.prefix = countryCode
    }
}
