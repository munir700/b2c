package com.yap.uikit.widgets.nameinitialscircleimageview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.yap.uikit.R
import com.yap.uikit.widgets.TextDrawable
import com.yap.uikit.widgets.nameinitialscircleimageview.imageloader.ImageDownloaderSingleton
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import java.util.regex.Pattern


class NameInitialsCircleImageView : CircleImageView {

    companion object {
        @DimenRes
        private val DEFAULT_TEXT_SIZE_SP = R.dimen.ui_kit_text_size_h8 //sp

        @ColorRes
        private val DEFAULT_TEXT_COLOR = android.R.color.white

        @DimenRes
        private val DEFAULT_WIDTH_DP = R.dimen._35sdp

        @DimenRes
        private val DEFAULT_HEIGHT_DP = R.dimen._35sdp
        private val DEFAULT_FONT = Typeface.DEFAULT

        @ColorRes
        private val DEFAULT_COLOR_GENERATOR = MaterialColorGenerator()

        private const val DEFAULT_ENABLE_SIGNATURE = false

        private const val DEFAULT_NAME = ""

        private const val DEFAULT_INDEX = -1

    }


    private var mEnableGlideSignature: Boolean
    private var mTextSizePixels: Int
    private var mWidthPixels: Int
    private var mHeightPixels: Int

    private var mFullName: String
    private var mTypeface: Typeface

    private var mIndex: Int

    @ColorInt
    private var mTextColor: Int

    @ColorInt
    private var mCircleBackgroundColor: Int

    private var mImageUrl: String? = null
    private var mColorGenerator: ColorGenerator

    constructor(context: Context?) : super(context) {
        init(null)
    }

    init {
        mIndex = DEFAULT_INDEX
        mFullName = DEFAULT_NAME
        mEnableGlideSignature = DEFAULT_ENABLE_SIGNATURE
        mTextSizePixels = context.resources.getDimensionPixelSize(DEFAULT_TEXT_SIZE_SP)
        mTextColor = ContextCompat.getColor(context, DEFAULT_TEXT_COLOR)
        mWidthPixels = context.resources.getDimensionPixelSize(DEFAULT_WIDTH_DP)
        mHeightPixels = context.resources.getDimensionPixelSize(DEFAULT_HEIGHT_DP)
        mTypeface = DEFAULT_FONT
        mColorGenerator = DEFAULT_COLOR_GENERATOR
        mCircleBackgroundColor = mColorGenerator.generateTextColor(0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        extractAttributes(attrs)
        updateImageDrawable()
    }

    private fun updateImageDrawable() {
        val url = mImageUrl
        val textDrawable = createRoundTextDrawable()
        if (url == null || url.isEmpty()) {
            setImageDrawable(textDrawable)
        } else {
            val signature =
                if (mEnableGlideSignature) System.currentTimeMillis().toString() else null
            ImageDownloaderSingleton.getImageDownloader(context)
                .downloadImage(context, url, this, textDrawable, signature)
        }
    }

    private fun extractAttributes(attrs: AttributeSet?) {
        attrs ?: return

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.NameInitialsCircleImageView)

        try {
            mTextSizePixels = typedArray.getDimensionPixelSize(
                R.styleable.NameInitialsCircleImageView_niTextSize,
                context.resources.getDimensionPixelSize(DEFAULT_TEXT_SIZE_SP)
            )

            mCircleBackgroundColor = typedArray.getColor(
                R.styleable.NameInitialsCircleImageView_circleBackgroundColor,
                mColorGenerator.generateBackgroundColor(0)
            )

            mTextColor = typedArray.getColor(
                R.styleable.NameInitialsCircleImageView_niTextColor,
                mColorGenerator.generateTextColor(0)
            )

            mIndex = typedArray.getColor(
                R.styleable.NameInitialsCircleImageView_niIndex,
                DEFAULT_INDEX
            )
            niImageUrl = typedArray.getString(
                R.styleable.NameInitialsCircleImageView_niImageUrl
            )

            mEnableGlideSignature = typedArray.getBoolean(
                R.styleable.NameInitialsCircleImageView_niEnableGlideSignature,
                DEFAULT_ENABLE_SIGNATURE
            )


            mFullName = typedArray.getString(R.styleable.NameInitialsCircleImageView_niText) ?: ""


            if (mIndex != -1) {
                mCircleBackgroundColor = mColorGenerator.generateBackgroundColor(mIndex)
                mTextColor = mColorGenerator.generateTextColor(mIndex)
            }

            mFullName = initialsWithEmojiSupport(mFullName)

            //check if there is font id specified by the developer
            if (typedArray.hasValue(R.styleable.NameInitialsCircleImageView_niTextFont)) {
                //fetch the resource id and load the font
                @FontRes val fontResId =
                    typedArray.getResourceId(R.styleable.NameInitialsCircleImageView_niTextFont, -1)
                val typeface = ResourcesCompat.getFont(context, fontResId)
                if (typeface != null) {
                    mTypeface = typeface
                }
            }
        } finally {
            typedArray.recycle()
        }
    }

    private fun createRoundTextDrawable(): TextDrawable {
        return TextDrawable.builder()
            .beginConfig()
            .textColor(mTextColor)
            .fontSize(mTextSizePixels)
            .useFont(mTypeface)
            .width(mWidthPixels)
            .height(mHeightPixels)
            .endConfig()
            .buildRound(mFullName, mCircleBackgroundColor)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidthPixels = w - paddingLeft - paddingRight
        mHeightPixels = h - paddingTop - paddingBottom
        updateImageDrawable()
    }

    /**
     * Sets the background color of the circle from a color resource
     *
     */
    @Deprecated("Use setImageInfo() instead", ReplaceWith("this.setImageInfo(imageInfo)"))
    override fun setCircleBackgroundColorResource(@ColorRes circleBackgroundColor: Int) {
        mCircleBackgroundColor = ContextCompat.getColor(context, circleBackgroundColor)
        updateImageDrawable()
    }

    /**
     * Sets the background color of the circle
     */
    @Deprecated("Use setImageInfo() instead", ReplaceWith("this.setImageInfo(imageInfo)"))
    override fun setCircleBackgroundColor(@ColorInt circleBackgroundColor: Int) {
        mCircleBackgroundColor = circleBackgroundColor
        updateImageDrawable()
    }

    override fun getCircleBackgroundColor(): Int {
        return mCircleBackgroundColor
    }

    fun setImageInfo(imageInfo: ImageInfo) {
        this.mFullName = initialsWithEmojiSupport(imageInfo.text)
        this.mIndex = imageInfo.index
        this.mTextColor = ContextCompat.getColor(context, imageInfo.textColorRes)
        this.mImageUrl = imageInfo.imageUrl
        //load font if specified
        if (imageInfo.fontResId != null) {
            val typeface = ResourcesCompat.getFont(context, imageInfo.fontResId)
            if (typeface != null) {
                mTypeface = typeface
            }
        }

        this.mColorGenerator = imageInfo.colorGenerator
        //load or generate color
        if (imageInfo.circleBackgroundColorRes != null) {
            this.mCircleBackgroundColor =
                ContextCompat.getColor(context, imageInfo.circleBackgroundColorRes)
        } else {
            this.mCircleBackgroundColor = mColorGenerator.generateBackgroundColor(0)
        }

        if (imageInfo.index != -1) {
            this.mCircleBackgroundColor = mColorGenerator.generateBackgroundColor(imageInfo.index)
            this.mTextColor = mColorGenerator.generateTextColor(imageInfo.index)
        }


        if (imageInfo.invalidateImageCache && imageInfo.imageUrl != null && imageInfo.imageUrl.isNotEmpty()) {
            ImageDownloaderSingleton.getImageDownloader(context)
                .invalidateImage(context, imageInfo.imageUrl)
        }

        updateImageDrawable()
    }

    private fun initialsWithEmojiSupport(name: String): String {
        val emo_regex =
            "(?:[\\uD83C\\uDF00-\\uD83D\\uDDFF]|[\\uD83E\\uDD00-\\uD83E\\uDDFF]|[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83D\\uDE80-\\uD83D\\uDEFF]|[\\u2600-\\u26FF]\\uFE0F?|[\\u2700-\\u27BF]\\uFE0F?|\\u24C2\\uFE0F?|[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}|[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C\\uDD91-\\uD83C\\uDD9A]\\uFE0F?|[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3|[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?|[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?|[\\u2934\\u2935]\\uFE0F?|[\\u3030\\u303D]\\uFE0F?|[\\u3297\\u3299]\\uFE0F?|[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?|[\\u203C\\u2049]\\uFE0F?|[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?|[\\u00A9\\u00AE]\\uFE0F?|[\\u2122\\u2139]\\uFE0F?|\\uD83C\\uDC04\\uFE0F?|\\uD83C\\uDCCF\\uFE0F?|[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?)"

        var cardFullName = name
        cardFullName = cardFullName.trim { it <= ' ' }
        var shortName = ""
        if (cardFullName.isNotEmpty() && cardFullName.contains(" ")) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            var firstName = nameStr[0]
            if (Character.isLetter(nameStr[0][0])) {
                firstName = nameStr[0].substring(0, 1)
            }

            var lastName = nameStr[nameStr.size - 1]
            if (Character.isLetter(nameStr[nameStr.size - 1][0])) {
                lastName = nameStr[nameStr.size - 1].substring(0, 1)
            }

            val firstNameMatcher = Pattern.compile(emo_regex).matcher(firstName)
            var isFirstEmoji = false
            var firstData = ""

            while (firstNameMatcher.find()) {
                firstData = firstNameMatcher.group()
                isFirstEmoji = true
                break
            }

            shortName = if (isFirstEmoji) {
                firstData
            } else {
                firstName.substring(0, 1)
            }

            val matcher = Pattern.compile(emo_regex).matcher(lastName)
            var isEmji = false
            var data = ""
            while (matcher.find()) {
                data = matcher.group()
                isEmji = true
                break
            }

            shortName += if (isEmji) {
                data
            } else {
                lastName.substring(0, 1)
            }
            return shortName.toUpperCase(Locale.getDefault())
        } else if (cardFullName.isNotEmpty()) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var firstName = nameStr[0]
            if (Character.isLetter(nameStr[0][0])) {
                firstName = nameStr[0].substring(0, 1)
            }

            val firstNameMatcher = Pattern.compile(emo_regex).matcher(firstName)
            var isFirstEmoji = false
            var firstData = ""

            while (firstNameMatcher.find()) {
                firstData = firstNameMatcher.group()
                isFirstEmoji = true
                break
            }

            shortName = if (isFirstEmoji) {
                firstData
            } else {
                firstName.substring(0, 1)
            }
            return shortName.toUpperCase(Locale.getDefault())
        }
        return shortName.toUpperCase(Locale.getDefault())
    }

    class ImageInfo(builder: Builder) {
        private val enableSignature: Boolean
        internal val index: Int

        internal val text: String

        @FontRes
        internal val fontResId: Int?

        @ColorRes
        internal val textColorRes: Int

        @ColorRes
        internal val circleBackgroundColorRes: Int?
        internal val imageUrl: String?
        internal val colorGenerator: ColorGenerator
        internal val invalidateImageCache: Boolean

        init {
            this.enableSignature = builder.enableSignature
            this.index = builder.index
            this.text = builder.text
            this.fontResId = builder.fontResId
            this.textColorRes = builder.textColorRes
            this.circleBackgroundColorRes = builder.circleBackgroundColorRes
            this.imageUrl = builder.imageUrl
            this.colorGenerator = builder.colorGenerator
            this.invalidateImageCache = builder.invalidateImageCache
        }

        class Builder(internal var text: String) {
            @FontRes
            internal var fontResId: Int? = null

            @ColorRes
            internal var textColorRes: Int = DEFAULT_TEXT_COLOR

            @ColorRes
            internal var circleBackgroundColorRes: Int? = null
            internal var colorGenerator: ColorGenerator = DEFAULT_COLOR_GENERATOR
            internal var imageUrl: String? = null
            internal var invalidateImageCache: Boolean = false
            internal var index: Int = -1
            internal var enableSignature: Boolean = false

            fun setText(text: String): Builder {
                this.text = text
                return this
            }

            fun setTextFont(@FontRes fontResId: Int?): Builder {
                this.fontResId = fontResId
                return this
            }

            fun setTextColor(@ColorRes textColorRes: Int): Builder {
                this.textColorRes = textColorRes
                return this
            }

            fun setCircleBackgroundColorRes(@ColorRes circleBackgroundColorRes: Int): Builder {
                this.circleBackgroundColorRes = circleBackgroundColorRes
                return this
            }

            fun setImageUrl(imageUrl: String?): Builder {
                this.imageUrl = imageUrl
                return this
            }

            fun setColorGenerator(colorGenerator: ColorGenerator): Builder {
                this.colorGenerator = colorGenerator;
                return this;
            }

            fun setInvalidateImageCache(invalidate: Boolean): Builder {
                this.invalidateImageCache = invalidate
                return this
            }

            fun setIndex(index: Int): Builder {
                this.index = index
                return this
            }

            fun setEnableSignature(isEnable: Boolean): Builder {
                this.enableSignature = isEnable
                return this
            }

            fun build(): ImageInfo {
                return ImageInfo(this)
            }
        }
    }

    var niIndex: Int? = null
        set(value) {
            field = value
            niIndex?.let {
                mIndex = it
                updateImageDrawable()
                invalidate()
            }
        }

    var niText: String? = null
        set(value) {
            field = value
            niText?.let {
                mFullName = it
                mFullName = initialsWithEmojiSupport(mFullName)
                updateImageDrawable()
                invalidate()
            }
        }

    var niImageUrl: String? = null
        set(value) {
            field = value
            niImageUrl?.let {
                mImageUrl = it
                updateImageDrawable()
                invalidate()
            }
        }

}