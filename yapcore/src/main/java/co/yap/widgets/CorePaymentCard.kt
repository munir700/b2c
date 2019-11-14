package co.yap.widgets

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.core_payment_card.view.*

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CorePaymentCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.core_payment_card, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CorePaymentCard, 0, 0)
//            mCardNickname =
//                typedArray.getString(R.styleable.CorePaymentCard_bank_nickname) ?: "CardNickname"
//            val cardNumber = typedArray.getString(R.styleable.CorePaymentCard_card_number)
//            val cardExpiry = typedArray.getString(R.styleable.CorePaymentCard_card_expiry)
            clMainContainer.background =
                typedArray.getDrawable(R.styleable.CorePaymentCard_card_background)
            ivCardType.setImageDrawable(typedArray.getDrawable(R.styleable.CorePaymentCard_card_type))
//            tvCardNumber.text = cardNumber
//            tvCardExpiry.text = cardExpiry
            typedArray.recycle()

        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }
    fun setCardNickname(cardName: String) {
        tvBankName.text = cardName
    }

    fun setCardNumber(cardNumber: String) {
        tvCardNumber.text = cardNumber
    }

    fun setCardExpiry(cardExpiry: String) {
        tvCardExpiry.text = cardExpiry
    }
}