package co.yap.yapcore.binders


import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.databinding.*
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Translator
import co.yap.widgets.CoreButton
import co.yap.widgets.CoreDialerPad
import co.yap.yapcore.R
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.enums.CardStatus
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.IBindable
import co.yap.yapcore.managers.MyUserManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import java.text.SimpleDateFormat
import java.util.*


object UIBinder {

    @BindingAdapter("bitmap")
    @JvmStatic
    fun setImageBitmap(view: ImageView, bitmap: Bitmap?) {
        if (bitmap != null)
            view.setImageBitmap(bitmap)
    }

    // Delivery status and core action Button
    @BindingAdapter("cardStatus")
    @JvmStatic
    fun setCardStatus(linearLayout: LinearLayout, card: Card) {
        when (CardStatus.valueOf(card.status)) {
            CardStatus.ACTIVE -> {
                linearLayout.visibility = View.GONE
            }
            CardStatus.BLOCKED -> {
                linearLayout.visibility = View.VISIBLE
            }
            CardStatus.INACTIVE -> {
                linearLayout.visibility = View.VISIBLE

            }
        }
    }

    // Top right card status image
    @BindingAdapter("cardStatus")
    @JvmStatic
    fun setCardStatus(imageView: ImageView, card: Card) {

        when (CardStatus.valueOf(card.status)) {
            CardStatus.ACTIVE -> {
                imageView.visibility = View.GONE
            }
            CardStatus.BLOCKED -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(R.drawable.ic_status_frozen)
            }
            CardStatus.INACTIVE -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(R.drawable.ic_status_ontheway)
            }

        }
    }

    // Card status message text
    @BindingAdapter("cardStatus")
    @JvmStatic
    fun setCardStatus(text: TextView, card: Card) {

        when (CardStatus.valueOf(card.status)) {
            CardStatus.ACTIVE -> {
                text.visibility = View.GONE
            }
            CardStatus.BLOCKED -> {
                text.visibility = View.VISIBLE
                text.text = Translator.getString(
                    text.context,
                    R.string.screen_cards_display_text_freeze_card
                )
            }
            CardStatus.INACTIVE -> {
                if (card.cardType == "DEBIT") {
                    if (MyUserManager.user?.notificationStatuses == "MEETING_SUCCESS") {
                        text.visibility = View.VISIBLE
                        text.text = Translator.getString(
                            text.context,
                            R.string.screen_cards_display_text_set_message
                        )
                    } else {
                        text.visibility = View.VISIBLE
                        text.text = Translator.getString(
                            text.context,
                            R.string.screen_cards_display_text_pending_delivery
                        )
                    }
                } else {
                    if (card.deliveryStatus == null) {
                        text.visibility = View.GONE
                    } else {
                        when (card.deliveryStatus?.let { CardDeliveryStatus.valueOf(it) }) {
                            CardDeliveryStatus.SHIPPED -> {
                                text.visibility = View.VISIBLE
                                text.text = Translator.getString(
                                    text.context,
                                    R.string.screen_cards_display_text_set_message
                                )
                            }
                            else -> {
                                text.visibility = View.VISIBLE
                                text.text = Translator.getString(
                                    text.context,
                                    R.string.screen_cards_display_text_pending_delivery
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Card status message text
    @BindingAdapter("underline_text")
    @JvmStatic
    fun setCardStatus(text: TextView, value: String) {
        val content = SpannableString(value)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        text.text = content
    }

    //Core action Button text
    @BindingAdapter("cardButtonStatus")
    @JvmStatic
    fun setcardButtonStatus(coreButton: TextView, card: Card) {

        when (CardStatus.valueOf(card.status)) {
            CardStatus.ACTIVE -> {
                coreButton.visibility = View.GONE
            }
            CardStatus.BLOCKED -> {
                coreButton.visibility = View.VISIBLE
                coreButton.text = Translator.getString(
                    coreButton.context,
                    R.string.screen_cards_button_unfreeze_card
                )
            }
            CardStatus.INACTIVE -> {
                if (card.cardType == "DEBIT") {
                    if (MyUserManager.user?.notificationStatuses == "MEETING_SUCCESS") {
                        coreButton.visibility = View.VISIBLE
                        coreButton.text = Translator.getString(
                            coreButton.context,
                            R.string.screen_cards_display_text_set_pin
                        )
                    } else {
                        coreButton.visibility = View.GONE
                    }
                } else {
                    if (card.deliveryStatus == null) {
                        coreButton.visibility = View.GONE
                    } else {
                        when (card.deliveryStatus?.let { CardDeliveryStatus.valueOf(it) }) {
                            CardDeliveryStatus.SHIPPED -> {
                                coreButton.visibility = View.VISIBLE
                                coreButton.text = Translator.getString(
                                    coreButton.context,
                                    R.string.screen_cards_display_text_set_pin
                                )
                            }
                            else -> {
                                coreButton.visibility = View.GONE
                            }
                        }
                    }
                }
            }

        }
    }

    private fun isExpire(expiryDate: String): Boolean {
        val today = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("MM/YY", Locale.ENGLISH)
        val expireDate = Calendar.getInstance()
        expireDate.time = dateFormatter.parse(expiryDate)

        var year = expireDate.get(Calendar.YEAR) - today.get(Calendar.YEAR)
        var month = expireDate.get(Calendar.MONTH) - today.get(Calendar.MONTH)
        return if (year > 0)
            false
        else
            !(year == 0 && month > 0)
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageResId(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

    @JvmStatic
    @BindingAdapter("CoreDialerError")
    fun setDialerErrorMessage(view: CoreDialerPad, error: String) {
        if (!error.isEmpty()) view.settingUIForError(error) else view.settingUIForNormal()
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageResId(view: ImageView, drawable: Drawable?) {
        if (drawable != null)
            view.setImageDrawable(drawable)
    }

    @BindingAdapter("text")
    @JvmStatic
    fun setText(view: TextView, text: String) {
        view.text = Translator.getString(view.context, text)
    }

    @BindingAdapter("text")
    @JvmStatic
    fun setText(view: TextView, textId: Int) {
        view.text = Translator.getString(view.context, textId)
    }

    @BindingAdapter("text", "concat")
    @JvmStatic
    fun setText(view: TextView, textKey: String, concat: Array<String>) {
        view.text = Translator.getString(view.context, textKey, *concat)
    }

    @BindingAdapter("text", "concat")
    @JvmStatic
    fun setText(view: TextView, textId: Int, concat: Array<String>) {
        view.text = Translator.getString(view.context, textId, *concat)
    }

    @BindingAdapter("text", "concat")
    @JvmStatic
    fun setText(view: TextView, textKey: String, concat: String) {
        view.text =
            Translator.getString(view.context, textKey, *StringUtils.toStringArray(concat))
    }

    @BindingAdapter("text", "concat")
    @JvmStatic
    fun setText(view: TextView, textId: Int, concat: String) {
        view.text =
            Translator.getString(view.context, textId, *StringUtils.toStringArray(concat))
    }

    @BindingAdapter("text", "start", "end")
    @JvmStatic
    fun setText(view: TextView, text: String, start: Int, end: Int) {
        val text1 = SpannableString(text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            text1.setSpan(
                ForegroundColorSpan(
                    view.context.resources.getColor(
                        R.color.colorPrimaryDark,
                        null
                    )
                ), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        } else {
            text1.setSpan(
                ForegroundColorSpan(view.context.resources.getColor(R.color.colorPrimaryDark)),
                start,
                end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
        view.text = text1
    }

    @BindingAdapter("hint")
    @JvmStatic
    fun setHint(view: TextView, textId: String) {
        view.hint = Translator.getString(view.context, textId)
    }

    @BindingAdapter("hint")
    @JvmStatic
    fun setHint(view: EditText, textId: String) {
        view.hint = Translator.getString(view.context, textId)
    }

    @BindingAdapter("selected")
    @JvmStatic
    fun setSelected(view: Button, selected: Boolean) {
        view.isSelected = selected
        view.isPressed = selected
    }

    @BindingAdapter("selected")
    @JvmStatic
    fun setSelected(view: TextView, selected: Boolean) {
        view.isSelected = selected
    }

    @BindingAdapter("enabled")
    @JvmStatic
    fun setEnabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
    }

    @BindingAdapter("entries", "layout")
    @JvmStatic
    fun <T : IBindable> setEntries(
        viewGroup: ViewGroup,
        entries: List<T>?, layoutId: Int
    ) {
        viewGroup.removeAllViews()
        if (entries != null) {
            val inflater =
                viewGroup.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            for (i in entries.indices) {
                val entry = entries[i]
                val binding =
                    DataBindingUtil.inflate<ViewDataBinding>(
                        inflater,
                        layoutId,
                        viewGroup,
                        true
                    )
                binding.setVariable(entry.bindingVariable, entry)
                binding.executePendingBindings()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("enableCoreButton")
    fun setEnable(view: CoreButton, enable: Boolean) {
        if (view != null) view.enableButton(enable)
    }

    @JvmStatic
    @BindingAdapter("componentDialerError")
    fun setDialerError(view: CoreDialerPad, error: String) {
        if (null != error && !error.isEmpty()) {
            view.settingUIForError(error)
        } else {
            view.settingUIForNormal()
        }

    }

    @JvmStatic
    @BindingAdapter("passcodeTextWatcher")
    fun te132mp(view: CoreDialerPad, watcher: TextWatcher) {
        view.editText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @BindingAdapter(value = ["otpAttrChanged"])
    fun setOtpListener(view: OtpTextView, listener: InverseBindingListener?) {

        if (listener != null) {
            view.otpListener = object : OTPListener {
                override fun onInteractionListener() {
                    // fired when user types something in the Otpbox
                    listener.onChange()
                }

                override fun onOTPComplete(otp: String) {
                    // fired when user has entered the OTP fully.

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("otp")
    fun setOtp(view: OtpTextView, value: String) {
        if (view.otp != value) {
            view.otp = value
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "otp")
    fun getOtp(view: OtpTextView): String = view.otp

    @JvmStatic
    @BindingAdapter(value = ["requestKeyboard", "forceKeyboard"], requireAll = false)
    fun setRequestKeyboard(view: View, request: Boolean, forced: Boolean) {
        Utils.requestKeyboard(view, request, forced)
    }

    //mobile Component
    @BindingAdapter("isCCpActivated")
    @JvmStatic
    fun isActivated(view: LinearLayout, isActive: Boolean) {
        view.isActivated = isActive
    }

    @BindingAdapter("setBackGroundRes")
    @JvmStatic
    fun setBgRes(view: LinearLayout, drawable: Drawable?) {
        view.background = drawable
        view.setPadding(
            view.context.resources.getDimensionPixelSize(R.dimen.margin_medium),
            0,
            view.context.resources.getDimensionPixelSize(R.dimen.margin_small),
            0
        )
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageResId(view: ImageView, path: String) {
        Glide.with(view.context)
            .load(path).centerCrop().placeholder(R.color.greyLight)
            .into(view)
    }

    @BindingAdapter("src", "circular")
    @JvmStatic
    fun setImageResId(view: ImageView, resId: Bitmap, circular: Boolean) {
        if (circular) {
            Glide.with(view.context)
                .asBitmap().load(resId).placeholder(R.color.greyLight)
                .transforms(CenterCrop(), RoundedCorners(15))
                .into(view)

        } else {

            Glide.with(view.context)
                .asBitmap().load(resId).placeholder(R.color.greyLight)
                .transforms(CenterCrop(), RoundedCorners(15))
                .into(view)
            //set placeholder here
        }
    }

    @BindingAdapter("toggleVisibility")
    @JvmStatic
    fun setImageResId(view: CardView, visibility: Boolean) {
        if (visibility) {
            view.visibility = View.VISIBLE
            YoYo.with(Techniques.SlideInUp)
                .duration(400)
                .playOn(view)


        } else {
            YoYo.with(Techniques.SlideOutDown)
                .duration(0)
                .playOn(view)

        }

    }

    @BindingAdapter("toggleButtonVisibility")
    @JvmStatic
    fun setImageResId(view: ImageView, visibility: Boolean) {
        if (visibility) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE

        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("textSelection")
    fun textSelection(view: EditText, selection: String) {
        if (!selection.isNullOrEmpty()) {

            view.setSelection(selection.length)

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("drawableClick")
    fun setDrawableRightListener(view: EditText, onDrawableClick: Boolean) {
        if (onDrawableClick) {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_field, 0)

            view.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, m: MotionEvent): Boolean {
                    var hasConsumed = false
                    if (v is EditText) {
                        if (m.x >= v.width - v.totalPaddingRight) {
                            if (m.action == MotionEvent.ACTION_UP) {
                                view.text.clear()

                                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                            }
                            hasConsumed = true
                        }
                    }
                    return hasConsumed
                }
            })
        } else {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        }
    }

    @JvmStatic
    @BindingAdapter("isLayoutActivated")
    fun setisLayoutActivated(view: LinearLayout, value: Boolean) {
//        if (!value) {
        view.isActivated = value

//        }
    }

     @BindingAdapter("src", "isRound")
    @JvmStatic
    fun setProfilePicture(view: ImageView, imageSrc: String, circular: Boolean) {
        Glide.with(view.context)
            .load(Uri.parse(imageSrc))
            .transforms(CenterCrop(), RoundedCorners(115))
            .into(view)

    }
}