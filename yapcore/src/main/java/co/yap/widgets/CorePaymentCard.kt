package co.yap.widgets

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.ThemeColorUtils
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.core_payment_card.view.*
import kotlin.math.roundToInt


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CorePaymentCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private var cardSizeType: Int = 2

    private var cardSizeTypeSmall: Int = 0
    private var cardSizeTypeMedium: Int = 1
    private var cardSizeTypeLarge: Int = 2

    var view: View = LayoutInflater.from(context)
        .inflate(R.layout.core_payment_card, this, true)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CorePaymentCard, 0, 0)
            cardSizeType = typedArray.getInt(
                R.styleable.CorePaymentCard_card_size_type,
                cardSizeTypeLarge
            )
            ivCardType.setImageDrawable(typedArray.getDrawable(R.styleable.CorePaymentCard_card_type))
            setCardSizeTypeDimensions()
            typedArray.recycle()
        }
    }

    fun setCardSizeTypeDimensions() {

        when (cardSizeType) {
            cardSizeTypeSmall -> {

                tvBankName.textSize = 5f
                tvCardNumber.textSize = 7f
                tvCardExpiry.textSize = 5f

                val ivChipHeight = context.applicationContext.resources.getDimension(R.dimen._8sdp)
                val ivChipWidth = context.applicationContext.resources.getDimension(R.dimen._8sdp)

                setUpImageDimensions(
                    ivCardType,
                    ivChipHeight.roundToInt(),
                    ivChipWidth.roundToInt()
                )

                setUpImageDimensions(
                    ivChip,
                    ivChipHeight.roundToInt(),
                    ivChipWidth.roundToInt()
                )

            }


            cardSizeTypeMedium -> {
//                ivChip         tvBankName       tvCardNumber        ivCardType                   tvCardExpiry
//medium           15/15           6sp             8SP                 5.9/16                       6SP

                tvBankName.textSize = 6f
                tvCardNumber.textSize = 8f
                tvCardExpiry.setTextSize(5f)

                val ivChipHeight = context.applicationContext.resources.getDimension(R.dimen._15sdp)
                val ivChipWidth = context.applicationContext.resources.getDimension(R.dimen._15sdp)

                setUpImageDimensions(ivChip, ivChipHeight.roundToInt(), ivChipWidth.roundToInt())
                setUpImageDimensions(
                    ivCardType,
                    ivChipHeight.roundToInt(),
                    ivChipWidth.roundToInt()
                )

            }

            else -> {
//                ivChip         tvBankName       tvCardNumber        ivCardType                   tvCardExpiry
//large              21/21           12SP            16sp                11/                         12sp


                tvBankName.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.applicationContext.resources.getDimension(R.dimen._10sdp)
                )
                tvCardNumber.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.applicationContext.resources.getDimension(R.dimen._13sdp)
                )
                tvCardExpiry.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.applicationContext.resources.getDimension(R.dimen._10sdp)
                )

                val ivChipHeight = context.applicationContext.resources.getDimension(R.dimen._21sdp)
                val ivChipWidth =
                    context.applicationContext.resources.getDimension(R.dimen._21sdp)//30

                setUpImageDimensions(ivChip, ivChipHeight.roundToInt(), ivChipWidth.roundToInt())
                setUpImageDimensions(
                    ivCardType,
                    ivChipHeight.roundToInt(),
                    ivChipWidth.roundToInt()
                )

            }
        }
    }

    private fun setUpImageDimensions(imageView: ImageView, newHeight: Int, newWidth: Int) {
        imageView.requestLayout()

        imageView.getLayoutParams().height = newHeight

        imageView.getLayoutParams().width = newWidth

//        imageView.setScaleType(ImageView.ScaleType.FIT_XY)
    }


    fun setCardNickname(cardName: String) {
        tvBankName.text = cardName
    }

    fun setCardNumber(cardNumber: String) {
        tvCardNumber.text = Utils.getFormattedCardNumber(cardNumber)
    }

    fun setCardExpiry(cardExpiry: String) {
        tvCardExpiry.text = DateUtils.convertTopUpDate(cardExpiry)
    }

    fun setCardBackground(bgCardColor: String) {
        try {
            val bg = clMainContainer.background
            bg.setTint(Color.parseColor("#$bgCardColor"))
        } catch (ex: IllegalArgumentException) {
            clMainContainer.setBackgroundColor(
                ThemeColorUtils.colorPrimaryDarkAttribute(context)
            )
        }
    }

    fun setCardLogoByType(cardType: String) {
        when (cardType) {
            Constants.VISA -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_visa_card
                )
            )
            Constants.MASTER -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_master_card_logo
                )
            )
            Constants.AMEX -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_amex_bank
                )
            )
            Constants.JCB -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_jcb_bank
                )
            )
            Constants.DINNERS -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_dinersclub_bank
                )
            )
            Constants.DISCOVER -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_discover_bank
                )
            )
            else -> {
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_visa_card
                )
            }

        }
    }

}