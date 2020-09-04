package co.yap.yapcore.helpers

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import co.yap.modules.carddetaildialog.CardDetailsDialogPagerAdapter
import co.yap.modules.carddetaildialog.CardDetailsModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.makeCall
import co.yap.yapcore.helpers.extentions.makeLinks
import co.yap.yapcore.managers.MyUserManager
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

/**
 * Display AlertDialog instantly with confirm
 *
 * @param[title] optional, title
 * @param[message] to display
 * @param[positiveButton] optional, button text
 * @param[negativeButton] optional, button text
 * @param[cancelable] able to cancel
 * @param[callback] callback of click ok button
 */
@JvmOverloads
fun Context.confirm(
    message: String,
    title: String = "",
    positiveButton: String? = null,
    negativeButton: String? = null,
    cancelable: Boolean = true,
    callback: DialogInterface.() -> Unit
) =
    AlertDialog.Builder(this).apply {
        if (title.isEmpty().not())
            setTitle(title)
        setMessage(message)
        setPositiveButton(
            positiveButton ?: getString(android.R.string.ok)
        ) { dialog, _ -> dialog.callback() }
        setNegativeButton(negativeButton ?: getString(android.R.string.no)) { _, _ -> }
        setCancelable(cancelable)
        show()
    }

/**
 * Display AlertDialog instantly with confirm
 *
 * @param[title] optional, title
 * @param[message] to display
 * @param[positiveButton] optional, button text
 * @param[negativeButton] optional, button text
 * @param[cancelable] able to cancel
 * @param[positiveCallback] callback of click ok button
 * @param[negativeCallback] callback of click cancel button
 */
@JvmOverloads
fun Context.confirm(
    message: String,
    title: String = "",
    positiveButton: String? = null,
    negativeButton: String? = null,
    cancelable: Boolean = true,
    positiveCallback: DialogInterface.() -> Unit,
    negativeCallback: DialogInterface.() -> Unit
) =
    AlertDialog.Builder(this).apply {
        if (title.isEmpty().not())
            setTitle(title)
        setMessage(message)
        setPositiveButton(
            positiveButton ?: getString(android.R.string.ok)
        )
        { dialog, _ -> dialog.positiveCallback() }
        setNegativeButton(
            negativeButton ?: getString(android.R.string.no)
        )
        { dialog, _ -> dialog.negativeCallback() }
        setCancelable(cancelable)
        show()
    }

/**
 * Display AlertDialog instantly with confirm
 *
 * @param[title] optional, title
 * @param[message] to display
 * @param[positiveButton] optional, button text
 * @param[negativeButton] optional, button text
 * @param[cancelable] able to cancel
 * @param[callback] callback of click ok button
 */
@JvmOverloads
fun Fragment.confirm(
    message: String,
    title: String? = "",
    positiveButton: String? = "Yes",
    negativeButton: String? = "No",
    cancelable: Boolean = true,
    callback: DialogInterface.() -> Unit
) =
    AlertDialog.Builder(requireContext()).apply {
        if (title?.isEmpty()?.not() == true)
            setTitle(title)
        setMessage(message)
        setPositiveButton(
            positiveButton ?: getString(android.R.string.ok)
        )
        { dialog, _ -> dialog.callback() }
        setNegativeButton(negativeButton ?: getString(android.R.string.no)) { _, _ -> }
        setCancelable(cancelable)
        show()
    }

@JvmOverloads
fun Fragment.confirm(
    message: String,
    title: String = "",
    positiveButton: String? = "Yes",
    negativeButton: String? = "No",
    cancelable: Boolean = true, themeId: Int = R.style.AlertDialogTheme,
    callback: DialogInterface.() -> Unit,
    negativeCallback: DialogInterface.() -> Unit
) =
    AlertDialog.Builder(requireContext(), themeId).apply {
        if (title.isEmpty().not())
            setTitle(title)
        setMessage(message)
        setPositiveButton(
            positiveButton ?: getString(android.R.string.ok)
        )
        { dialog, _ -> dialog.callback() }
        setNegativeButton(
            negativeButton ?: getString(android.R.string.no)
        )
        { dialog, _ -> dialog.negativeCallback() }
        //setNegativeButton(negativeButton ?: getString(android.R.string.no)) { _, _ -> }
        setCancelable(cancelable)
        show()
    }

@JvmOverloads
fun Fragment.confirm(
    message: CharSequence,
    title: String = "",
    positiveButton: String? = "Yes",
    negativeButton: String? = "No",
    cancelable: Boolean = true, themeId: Int = R.style.AlertDialogTheme,
    callback: DialogInterface.() -> Unit,
    negativeCallback: DialogInterface.() -> Unit
) =
    AlertDialog.Builder(requireContext(), themeId).apply {
        if (title.isEmpty().not())
            setTitle(title)
        setMessage(message)
        setPositiveButton(
            positiveButton ?: getString(android.R.string.ok)
        )
        { dialog, _ -> dialog.callback() }
        setNegativeButton(
            negativeButton ?: getString(android.R.string.no)
        )
        { dialog, _ -> dialog.negativeCallback() }
        //setNegativeButton(negativeButton ?: getString(android.R.string.no)) { _, _ -> }
        setCancelable(cancelable)
        show()
    }

/**
 * Display AlertDialog instantly
 *
 * @param[title] optional, title
 * @param[message] to display
 * @param[positiveButton] optional, button text
 * @param[cancelable] able to cancel
 * @param[callback] callback of click ok button
 */
@JvmOverloads
fun Context.alert(
    message: String,
    title: String = "",
    positiveButton: String? = null,
    cancelable: Boolean = true,
    callback: (DialogInterface) -> Unit = {}
) =
    AlertDialog.Builder(this).apply {
        if (title.isEmpty().not())
            setTitle(title)
        setMessage(message)
        setPositiveButton(positiveButton ?: getString(android.R.string.ok)) { dialog, _ ->
            callback(
                dialog
            )
        }
        setCancelable(cancelable)
        show()
    }

/**
 * Display AlertDialog instantly
 *
 * @param[title] optional, title
 * @param[message] to display
 * @param[positiveButton] optional, button text
 * @param[cancelable] able to cancel
 * @param[callback] callback of click ok button
 */
@JvmOverloads
fun Fragment.alert(
    message: String,
    title: String = "",
    positiveButton: String? = null,
    cancelable: Boolean = true,
    callback: (DialogInterface) -> Unit = {}
) = requireContext().alert(message, title, positiveButton, cancelable, callback)

/**
 * Display AlertDialog instantly
 *
 * @param[title] optional, title
 * @param[message] to display
 * @param[positiveButton] optional, button text
 * @param[cancelable] able to cancel
 * @param[callback] callback of click ok button
 */
@JvmOverloads
fun AppCompatActivity.alert(
    message: String,
    title: String = "",
    positiveButton: String? = null,
    cancelable: Boolean = true,
    callback: (DialogInterface) -> Unit = {}
) {
    this.alert(message, title, positiveButton, cancelable, callback)
}

fun Context.showCardDetailsPopup(cardDetail: CardDetail?, card: Card?) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_card_details)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val btnClose = dialog.findViewById(R.id.ivCross) as ImageView
    var cardType = ""
    var cardNumber: String? = ""
    if (null != cardDetail?.cardNumber) {
        if (cardDetail.cardNumber?.trim()?.contains(" ") == true) {
            cardNumber = cardDetail.cardNumber
        } else {
            if (cardDetail.cardNumber?.length == 16) {
                val formattedCardNumber: StringBuilder =
                    StringBuilder(cardDetail.cardNumber ?: "")
                formattedCardNumber.insert(4, " ")
                formattedCardNumber.insert(9, " ")
                formattedCardNumber.insert(14, " ")
                cardNumber = formattedCardNumber.toString()
            }
        }
    }

    if (Constants.CARD_TYPE_DEBIT == card?.cardType) {
        cardType = "Primary card"
    } else {
        cardType = if (card?.nameUpdated == true) {
            card.cardName
        } else {
            if (card?.physical == true) {
                Constants.TEXT_SPARE_CARD_PHYSICAL
            } else {
                Constants.TEXT_SPARE_CARD_VIRTUAL
            }
        }
    }

    btnClose.setOnClickListener {
        dialog.dismiss()
    }
    val indicator = dialog.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
    val viewPager = dialog.findViewById<ViewPager2>(R.id.cardsPager)
    val pagerList = mutableListOf<CardDetailsModel>()
    pagerList.add(
        CardDetailsModel(
            cardExpiry = cardDetail?.expiryDate,
            cardType = cardType,
            cardNumber = cardNumber, cardCvv = cardDetail?.cvv
        )
    )
    pagerList.add(
        CardDetailsModel(
            cardExpiry = cardDetail?.expiryDate,
            cardType = cardType,
            cardNumber = cardNumber, cardCvv = cardDetail?.cvv
        )
    )
    val cardDetailsPagerAdapter = CardDetailsDialogPagerAdapter(pagerList)
    viewPager?.adapter = cardDetailsPagerAdapter
    indicator?.setViewPager2(viewPager)
    dialog.show()
}

fun Context.showYapAlertDialog(
    title: String? = null,
    message: String?
) {
    val builder = android.app.AlertDialog.Builder(this)
    var alertDialog: android.app.AlertDialog? = null
    val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    title?.let { builder.setTitle(title) }
    val dialogLayout: View =
        inflater.inflate(R.layout.alert_dialogue, null)
    val label = dialogLayout.findViewById<TextView>(R.id.tvTitle)
    label.text = message
    val ok = dialogLayout.findViewById<TextView>(R.id.tvButtonTitle)
    ok.text = "OK"
    ok.setOnClickListener {
        alertDialog?.dismiss()
    }

    builder.setView(dialogLayout)
    builder.setCancelable(false)
    alertDialog = builder.create()

    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()

}

fun Activity.showAlertDialogAndExitApp(
    title: String? = null,
    message: String?,
    buttonText: String = "OK",
    callback: () -> Unit = {},
    closeActivity: Boolean = true,
    isOtpBlocked: Boolean = false
) {
    val builder = android.app.AlertDialog.Builder(this)
    var alertDialog: android.app.AlertDialog? = null
    val inflater: LayoutInflater = layoutInflater
    title?.let { builder.setTitle(title) }
    val dialogLayout: View =
        inflater.inflate(R.layout.alert_dialogue, null)
    val label = dialogLayout.findViewById<TextView>(R.id.tvTitle)
    label.text = message
    val ok = dialogLayout.findViewById<TextView>(R.id.tvButtonTitle)
    ok.text = buttonText
    ok.setOnClickListener {
        alertDialog?.dismiss()
        if (closeActivity)
            finish()
        callback()
    }
    if (isOtpBlocked) {
        label.makeLinks(Pair(MyUserManager.helpPhoneNumber, View.OnClickListener {
            makeCall(MyUserManager.helpPhoneNumber)
        }))
    }

    builder.setView(dialogLayout)
    builder.setCancelable(false)
    alertDialog = builder.create()

    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()
}
