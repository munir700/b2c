package co.yap.yapcore.helpers

import android.net.Uri
import android.view.View
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
import co.yap.yapcore.enums.YAPForYouGoalMedia
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.getMerchantCategoryIcon
import co.yap.yapcore.helpers.extentions.loadCardImage
import co.yap.yapcore.helpers.glide.setCircleCropImage
import co.yap.yapcore.helpers.glide.setImage
import com.airbnb.lottie.LottieAnimationView

object ImageBinding {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: AppCompatImageView, url: String) {
        setImage(imageView, url)
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            setImage(imageView, url)
        }
    }

    @JvmStatic
    @BindingAdapter("cardImageUrl")
    fun setCardImageUrl(imageView: AppCompatImageView, url: String) {
        imageView.loadCardImage(url)
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: AppCompatImageView, url: String?) {
        url?.let {
            setCircleCropImage(imageView, url)
        }
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
            .textColor(ContextCompat.getColor(imageView.context, R.color.purple))
        setCircleCropImage(
            imageView,
            beneficiaryPicture ?: "",
            builder.buildRect(
                Utils.shortName(fullName ?: ""),
                ContextCompat.getColor(imageView.context, R.color.disabledLight)
            )
        )
    }

    fun loadAvatar(
        imageView: ImageView,
        beneficiaryPicture: String?,
        fullName: String?, @ColorRes color: Int, @DimenRes fontSize: Int = R.dimen.text_size_h5
    ) {

        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._35sdp))
            .height(imageView.context.dimen(R.dimen._35sdp))
            .fontSize(imageView.context.dimen(fontSize))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!).bold()
            .toUpperCase()
            .textColor(ContextCompat.getColor(imageView.context, R.color.colorPrimary))
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

    @JvmStatic
    @BindingAdapter(value = ["media", "completedMedia"], requireAll = false)
    fun loadLottieAnimation(
        lottieView: LottieAnimationView,
        media: YAPForYouGoalMedia,
        completedMedia: YAPForYouGoalMedia? = null
    ) {
        if (completedMedia == null) {
            when (media) {
                is YAPForYouGoalMedia.Image -> {
                    val id = lottieView.context.resources.getIdentifier(
                        media.imageName,
                        "drawable",
                        lottieView.context.packageName
                    )
                    val drawable = lottieView.context.resources.getDrawable(id, null)
                    lottieView.setImageDrawable(
                        drawable
                    )
                }
                is YAPForYouGoalMedia.LottieAnimation -> {
                    lottieView.setAnimation(media.jsonFileName)
                }

                is YAPForYouGoalMedia.None -> {
                    lottieView.visibility = View.INVISIBLE
                }
            }
        } else {
            when (completedMedia) {
                is YAPForYouGoalMedia.Image -> {
                    val id = lottieView.context.resources.getIdentifier(
                        completedMedia.imageName,
                        "drawable",
                        lottieView.context.packageName
                    )
                    val drawable = lottieView.context.resources.getDrawable(id, null)
                    lottieView.setImageDrawable(
                        drawable
                    )
                }

                is YAPForYouGoalMedia.LottieAnimation -> {
                    lottieView.setAnimation(completedMedia.jsonFileName)
                }

                is YAPForYouGoalMedia.None -> {
                    lottieView.visibility = View.INVISIBLE
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("urlMedia")
    fun setCircularImageUrlYapForYou(imageView: CoreCircularImageView, goal: YAPForYouGoalMedia?) {
        goal?.let {
            when (goal) {
                is YAPForYouGoalMedia.ImageUrl -> {
                    if (goal.imageUrl.isNullOrBlank())
                        imageView.visibility = View.GONE
                    else
                        setCircularImageUrl(imageView, goal.imageUrl)
                }
                else -> {

                }
            }
        }
    }
}