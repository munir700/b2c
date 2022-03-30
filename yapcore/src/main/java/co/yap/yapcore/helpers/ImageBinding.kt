package co.yap.yapcore.helpers

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Keep
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.BindingAdapter
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.widgets.CoreCircularImageView
import co.yap.widgets.PrefixSuffixEditText
import co.yap.widgets.TextDrawable
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.YAPForYouGoalMedia
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.getMerchantCategoryIcon
import co.yap.yapcore.helpers.extentions.loadCardImage
import co.yap.yapcore.helpers.glide.setCircleCropImage
import co.yap.yapcore.helpers.glide.setImage
import co.yap.yapcore.helpers.glide.setImage1
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.liveperson.infra.configuration.Configuration.getDimension
import com.liveperson.infra.utils.Utils.getResources
import kotlin.math.roundToInt

@Keep
object ImageBinding {
    @JvmStatic
    @BindingAdapter(value = ["imageUrlWidget", "drawableResource"])
    fun setImageUrl(imageView: AppCompatImageView, url: String?, drawableResource: Drawable) {
        url?.let { setImage1(imageView, it) } ?: setImage(imageView, drawableResource)
    }

    @JvmStatic
    @BindingAdapter("drawable")
    fun setImageDrawable(imageView: AppCompatImageView, drawable: Drawable?) {
        drawable?.let {
            setImage(imageView, drawable)
        }

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
    fun setCircularImageUrl(imageView: AppCompatImageView, uri: Uri?) {
        uri?.let { setImage(imageView, uri) }

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
        imageUrl: String?,
        fullName: String?, @ColorRes colorCode: Int, @DimenRes fontSize: Int = R.dimen.text_size_h5
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
            imageUrl ?: "",
            builder.buildRect(
                Utils.shortName(fullName ?: ""),
                ContextCompat.getColor(imageView.context, colorCode)
            )
        )
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "fullName", "colorCode"], requireAll = false)
    fun loadAvatar(
        imageView: ImageView,
        imageUrl: String?,
        fullName: String?, colorCode: Int?
    ) {
        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(R.dimen._35sdp))
            .height(imageView.context.dimen(R.dimen._35sdp))
            .fontSize(imageView.context.dimen(R.dimen.text_size_h1))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!).bold()
            .toUpperCase()
            .textColor(colorCode ?: -1)
        setCircleCropImage(
            imageView,
            imageUrl ?: "",
            builder.buildRect(
                Utils.shortName(fullName ?: ""),
                ColorUtils.setAlphaComponent(colorCode ?: -1, 25)
            ), System.currentTimeMillis().toString()
        )
    }

    fun loadAvatar(
        imageView: ImageView,
        isCircular: Boolean,
        beneficiaryPicture: String?,
        fullName: String?,
        @ColorRes color: Int,
        @DimenRes fontSize: Int = R.dimen.text_size_h5,
        @ColorRes textColor: Int = R.color.colorPrimary,
        @DimenRes imageSIze: Int = R.dimen._35sdp
    ) {

        val builder = TextDrawable.builder()
        builder.beginConfig().width(imageView.context.dimen(imageSIze))
            .height(imageView.context.dimen(imageSIze))
            .fontSize(imageView.context.dimen(fontSize))
            .useFont(ResourcesCompat.getFont(imageView.context, R.font.roboto_regular)!!).bold()
            .toUpperCase()
            .textColor(ContextCompat.getColor(imageView.context, textColor))
        setCircleCropImage(
            imageView,
            beneficiaryPicture ?: "", if (isCircular)
                builder.buildRound(
                    Utils.shortName(fullName ?: ""),
                    ContextCompat.getColor(imageView.context, color)
                ) else builder.buildRect(
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
            ),
            SharedPreferenceManager.getInstance(imageView.context)
                .getValueString(Constants.KEY_IMAGE_LOADING_TIME)
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
            if (isBackground) getResId(imageView.context,"ic_${getDrawableName(fName)}") else fName.getMerchantCategoryIcon(imageView.context)

        if (resId >0) {
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
        if (resource > 0)
            imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:srcCompatGif", "imageUrl", "fullName", "bgColor", "initialTextSize", "initialTextColor", "imageSize"],
        requireAll = true
    )
    fun setGifImageViewResource(
        imageView: AppCompatImageView, resourceId: Int?, imageUrl: String?,
        fullName: String?,
        bgColor: Int, initialTextSize: Int,
        initialTextColor: Int,
        imageSize: Int
    ) {
        resourceId?.let { loadGifImageView(imageView, it) } ?: loadAvatar(
            imageView, false,
            imageUrl,
            fullName,
            bgColor,
            initialTextSize,
            initialTextColor,
            imageSize
        )
    }

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "fullName", "bgColor", "initialTextSize", "initialTextColor", "imageSize"],
        requireAll = true
    )
    fun setImageViewResource(
        imageView: ShapeableImageView, imageUrl: String?,
        fullName: String?,
        bgColor: String, initialTextSize: Int,
        initialTextColor: Int,
        imageSize: Int
    ) {
        imageUrl?.let {
            if (imageUrl.isNullOrEmpty().not()) {
                setImage1(imageView, it)
                imageView.shapeAppearanceModel =
                    imageView.shapeAppearanceModel
                        .toBuilder()
                        .setTopLeftCorner(CornerFamily.ROUNDED, imageView.context.resources.getDimension(R.dimen.margin_normal_large))
                        .setTopRightCorner(CornerFamily.ROUNDED, imageView.context.resources.getDimension(R.dimen.margin_normal_large))

                        .build()
            } else {
                loadAvatar(
                    imageView, false,
                    imageUrl,
                    fullName,
                    R.color.colorPrimaryDark,
                    initialTextSize,
                    initialTextColor,
                    imageSize
                )
            }
        } ?: loadAvatar(
            imageView, false,
            imageUrl,
            fullName,
            R.color.colorPrimaryDark,
            initialTextSize,
            initialTextColor,
            imageSize
        )
    }

    fun loadGifImageView(
        imageView: AppCompatImageView?,
        resource: Int,
        loopCount: Int = 1,
        delayBetweenLoop: Long = 100L,
        isLoop: Boolean = false,
        onAnimationComplete: (() -> Unit?)? = null
    ) {
        var countPlay = 0
        if (resource > 0) {
            imageView?.let {
                Glide.with(it.context).asGif().load(resource)
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
                            resource?.registerAnimationCallback(object :
                                Animatable2Compat.AnimationCallback() {
                                override fun onAnimationStart(drawable: Drawable?) {
                                    super.onAnimationStart(drawable)
                                }

                                override fun onAnimationEnd(drawable: Drawable?) {
                                    super.onAnimationEnd(drawable)
                                    countPlay++
                                    if (isLoop || countPlay < loopCount) {
                                        it.postDelayed({
                                            resource.startFromFirstFrame()
                                        }, delayBetweenLoop)
                                    } else {
                                        onAnimationComplete?.let { it1 -> it1() }
                                    }
                                }

                            })
                            return false
                        }
                    }).into(it)
            }
        }
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
                imageView.context,
                "flag_${getDrawableName(it)}"
            )
            if (resId > 0) {
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

    fun getResId(context: Context, drawableName: String): Int {
        return try {
            context.resources.getIdentifier(drawableName, "drawable", context.packageName)
//            val res = R.drawable::class.java
//            val field = res.getField(drawableName)
//            field.getInt(null)
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

        val resId = getResId(view.context,
            "flag_${
                getDrawableName(
                    countryName
                )
            }"
        )
        if (resId >0) {
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

                else -> {
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

                else -> {
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

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl", "fullName", "position", "isBackground", "showFirstInitials", "imageDrawable"],
        requireAll = false
    )
    fun loadAnalyticsAvatar(
        imageView: ImageView,
        imageUrl: String?,
        fullName: String?,
        position: Int,
        isBackground: Boolean = true,
        showFirstInitials: Boolean = false, imageDrawable: Drawable?
    ) {
        if (fullName.isNullOrEmpty()) return
        val fName = fullName ?: ""

        val colors = imageView.context.resources.getIntArray(R.array.analyticsColors)
        val resId =
            if (isBackground) getResId(imageView.context,"ic_${getDrawableName(fName)}") else fName.getMerchantCategoryIcon(imageView.context)

        if (resId >0) {
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
            if (imageDrawable != null) imageView.setImageDrawable(imageDrawable)
            else setDrawable(
                imageView,
                imageUrl,
                if (showFirstInitials) fName.split(" ")[0] else fName,
                position
            )
        }
    }

    /*
       New Binding Adapter for Analytics Categories it displays icons from api response along with circular background
    */
    @JvmStatic
    @BindingAdapter(
        value = ["imageLogo", "categoryTitle", "position", "isBackground", "showFirstInitials", "categoryColor", "detailType"],
        requireAll = false
    )
    fun loadCategoryAvatar(
        imageView: ImageView,
        imageLogo: String?,
        categoryTitle: String?,
        position: Int,
        isBackground: Boolean = true,
        showFirstInitials: Boolean = false,
        categoryColor: String? = "",
        detailType: String? = Constants.MERCHANT_CATEGORY_ID
    ) {
        if (categoryTitle.isNullOrEmpty()) return
        val fName = categoryTitle ?: ""
        when {
            imageLogo.isNullOrEmpty().not() && imageLogo != " " -> {
                Glide.with(imageView)
                    .asBitmap()
                    .load(imageLogo)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val resImg = BitmapDrawable(
                                getResources(),
                                resource
                            )
                            imageView.setImageDrawable(resImg)
                            if (isBackground) {
                                detailType?.let { type ->
                                    if (type.contentEquals(Constants.MERCHANT_NAME)) {
                                        val roundedBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(
                                                getResources(),
                                                resource
                                            )
                                        roundedBitmapDrawable.cornerRadius = 300.0f
                                        roundedBitmapDrawable.setAntiAlias(true)
                                        imageView.setImageDrawable(roundedBitmapDrawable)
                                    } else setCategoryIcon(categoryColor, imageView, position)
                                } ?: setCategoryIcon(categoryColor, imageView, position)
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
            else -> {
                setDrawableWithoutURL(imageView, position, showFirstInitials, fName)
            }
        }
    }

    private fun setCategoryDrawable(imageView: ImageView, color: Int) {
        val oval = ShapeDrawable(OvalShape())
        oval.intrinsicHeight = 50
        oval.intrinsicWidth = 50
        oval.paint.color = color
        imageView.background = oval
    }

    private fun getColorWithAlpha(color: Int, ratio: Float): Int {
        return Color.argb(
            (Color.alpha(color) * ratio).roundToInt(),
            Color.red(color),
            Color.green(color),
            Color.blue(color)
        )
    }

    private fun getTintBitmap(imageView: ImageView, resource: Bitmap, position: Int): Bitmap {
        val colors = imageView.context.resources.getIntArray(R.array.analyticsColors)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(
            getAnalyticsColor(
                colors,
                position
            ), PorterDuff.Mode.SRC_IN
        )
        val bitmapResult = Bitmap.createBitmap(
            resource.getWidth(),
            resource.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmapResult)
        canvas.drawBitmap(resource, 0f, 0f, paint)
        return bitmapResult
    }

    fun setCategoryIcon(categoryColor: String?, imageView: ImageView, position: Int) {
        categoryColor?.let { category ->
            val colorCode = Utils.categoryColorValidation(category)
            if (colorCode != -1)
                setCategoryDrawable(
                    imageView,
                    getColorWithAlpha(
                        colorCode,
                        0.20f
                    )
                )
        } ?: setCategoryDrawable(
            imageView, Utils.getBackgroundColorForAnalytics(
                imageView.context,
                position = position
            )
        )
    }

    fun setDrawableWithoutURL(
        imageView: ImageView,
        position: Int,
        showFirstInitials: Boolean,
        fName: String
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
        val fallBack = builder.buildRect(
            Utils.shortName(if (showFirstInitials) fName.split(" ")[0] else fName),
            Color.TRANSPARENT
        )
        imageView.setImageDrawable(fallBack)
        setCategoryDrawable(
            imageView,
            Utils.getBackgroundColorForAnalytics(imageView.context, position = position)
        )
    }

    @JvmStatic
    @BindingAdapter(value = ["resName", "isFlag"], requireAll = true)
    fun setDrawableWithName(imageView: ImageView, resourceName: String, isFlag: Boolean) {
        if (isFlag) imageView.setImageResource(CurrencyUtils.getFlagDrawable(imageView.context, resourceName))
        else
            imageView.setImageResource(
                getResId(
                    imageView.context,
                    "ic_${getDrawableName(resourceName)}"
                )
            )
    }
}