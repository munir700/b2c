package co.yap.widgets

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import co.yap.yapcore.R
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.core_payment_card.view.*

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CorePaymentCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    var view: View = LayoutInflater.from(context)
        .inflate(R.layout.core_payment_card, this, true)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CorePaymentCard, 0, 0)
            ivCardType.setImageDrawable(typedArray.getDrawable(R.styleable.CorePaymentCard_card_type))
            typedArray.recycle()
        }
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
            clMainContainer.setBackgroundColor(Color.parseColor(bgCardColor))
        } catch (ex: IllegalArgumentException) {
            clMainContainer.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
            )
        }
    }

    fun setCardLogoByType(cardType: String) {
        when (cardType) {
            "VISA" -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_visa_card
                )
            )
            "MASTERCARD" -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_logo_master
                )
            )
            "AMEX" -> ivCardType.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_american_express_logo
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