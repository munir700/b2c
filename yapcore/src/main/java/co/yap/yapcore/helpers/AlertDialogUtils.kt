package co.yap.yapcore.helpers

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import co.yap.widgets.CoreButton
import co.yap.widgets.setOnClick
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ConfirmAlertDialogBinding
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.managers.SessionManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
fun Activity.confirm(
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
    title: String = "",
    positiveButton: String? = "Yes",
    negativeButton: String? = "No",
    cancelable: Boolean = true,
    callback: DialogInterface.() -> Unit
) =
    AlertDialog.Builder(requireContext()).apply {
        if (title.isEmpty().not())
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

fun Activity.showAlertCustomDialog(
    title: String? = "",
    message: String? = "",
    buttonText: String? = "OK"
) {
    val dialogLayout = Dialog(this)
    dialogLayout.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialogLayout.setCancelable(false)
    dialogLayout.setContentView(R.layout.alert_dialogue_custom)
    val dialogTitle = dialogLayout.findViewById<TextView>(R.id.tvDialogTitle)
    val label = dialogLayout.findViewById<TextView>(R.id.tvTitle)
    label.text = message
    dialogTitle.text = title
    val ok = dialogLayout.findViewById<CoreButton>(R.id.btnAction)
    ok.text = buttonText
    ok.setOnClickListener {
        dialogLayout.dismiss()
    }
    dialogLayout.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialogLayout.show()

}

fun Activity.showAlertDialogAndExitApp(
    Title: String? = null,
    dialogTitle: String? = "",
    message: String?,
    leftButtonText: String = "OK",
    rightButtonText: String = "Cancel",
    callback: () -> Unit = {},
    titleVisibility: Boolean = false,
    closeActivity: Boolean = true,
    isOtpBlocked: Boolean = false,
    isTwoButton: Boolean = false
) {
    val builder = android.app.AlertDialog.Builder(this)
    var alertDialog: android.app.AlertDialog? = null
    val inflater: LayoutInflater = layoutInflater
    Title?.let { builder.setTitle(Title) }
    val dialogLayout: View =
        inflater.inflate(R.layout.alert_dialogue, null)
    val label = dialogLayout.findViewById<TextView>(R.id.tvTitle)
    label.text = message
    val dTitle = dialogLayout.findViewById<TextView>(R.id.tvDialogTitle)
    val cancel = dialogLayout.findViewById<TextView>(R.id.tvButtonCancel)
    val ok = dialogLayout.findViewById<TextView>(R.id.tvButtonTitle)
    val btnDivider = dialogLayout.findViewById<View>(R.id.btnDivider)
    ok.text = leftButtonText
    cancel.text = rightButtonText
    cancel.setOnClickListener {
        alertDialog?.dismiss()
    }
    if (titleVisibility) {
        dTitle.text = dialogTitle
        dTitle.visibility = View.VISIBLE
    }
    ok.setOnClickListener {
        alertDialog?.dismiss()
        if (closeActivity)
            finish()
        callback()
    }

    if (isTwoButton) {
        cancel.visibility = View.VISIBLE
        btnDivider.visibility = View.VISIBLE
    }
    if (isOtpBlocked) {
        label.makeLinks(
            Pair(SessionManager.helpPhoneNumber, View.OnClickListener {
                makeCall(SessionManager.helpPhoneNumber)
            }),
            Pair("live chat", View.OnClickListener {
                this@showAlertDialogAndExitApp.chatSetup()
            })
        )
    }

    builder.setView(dialogLayout)
    builder.setCancelable(false)
    alertDialog = builder.create()

    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()
}


fun Activity.showReceiptSuccessDialog(
    description: String? = null,
    addOtherVisibility: Boolean? = true,
    addAnotherText: String? = "",
    callback: (Int) -> Unit = {}
) {
    val dialogLayout = Dialog(this)
    dialogLayout.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialogLayout.setCancelable(false)
    dialogLayout.setContentView(R.layout.layout_receipt_success_dialog)
    val label = dialogLayout.findViewById<TextView>(R.id.tvDescrip)
    val addAnother = dialogLayout.findViewById<TextView>(R.id.tvAddAnother)
    val coreButton = dialogLayout.findViewById<TextView>(R.id.btnActionDone)
    label.text = description
    addAnother.text = addAnotherText
    addAnother.visibility = if (addOtherVisibility == true) View.VISIBLE else View.GONE
    coreButton.setOnClickListener {
        dialogLayout.dismiss()
        callback.invoke(coreButton.id)
    }

    addAnother.setOnClickListener {
        callback.invoke(addAnother.id)
        dialogLayout.dismiss()
    }

    dialogLayout.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialogLayout.show()
}

fun Context.infoDialog(
    title: String = "",
    message: String,
    buttonText: String? = null,
    callback: () -> Unit = {}
) {

    val dialogLayout = Dialog(this)
    dialogLayout.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialogLayout.setCancelable(false)
    dialogLayout.setContentView(R.layout.dialog_information)
    val dialogTitle = dialogLayout.findViewById<TextView>(R.id.tvDialogTitle)
    val messageView = dialogLayout.findViewById<TextView>(R.id.tvMessage)
    messageView.text = message
    dialogTitle.text = title
    val btnClose = dialogLayout.findViewById<AppCompatTextView>(R.id.btnClose)
    dialogLayout.findViewById<View>(R.id.btnNext).visibility = View.GONE
    btnClose.text = buttonText
    btnClose.setOnClickListener {
        callback.invoke()
        dialogLayout.dismiss()
    }
    dialogLayout.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialogLayout.show()
}

fun Context.beneficiaryInfoDialog(
    title: String = "",
    message: String,
    buttonText: String? = null,
    callback: (proceed: Boolean) -> Unit = {},
    icon: Int? = null,
    coreButtonTitle: String? = null
) {

    val dialogLayout = Dialog(this)
    dialogLayout.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialogLayout.setCancelable(false)
    dialogLayout.setContentView(R.layout.dialog_information)
    val dialogTitle = dialogLayout.findViewById<TextView>(R.id.tvDialogTitle)
    val messageView = dialogLayout.findViewById<TextView>(R.id.tvMessage)
    val tbBtnInfo = dialogLayout.findViewById<AppCompatImageView>(R.id.tbBtnInfo)
    messageView.text = message
    dialogTitle.text = title
    icon?.let { resource ->
        tbBtnInfo.setImageResource(resource)
    }
    val btnClose = dialogLayout.findViewById<AppCompatTextView>(R.id.btnClose)
    val btnNext = dialogLayout.findViewById<CoreButton>(R.id.btnNext)
    btnClose.text = buttonText
    btnClose.setOnClickListener {
        callback.invoke(false)
        dialogLayout.dismiss()
    }
    coreButtonTitle?.let { label ->
        btnNext.visibility = View.VISIBLE
        btnNext.text = label
        btnNext.setOnClickListener {
            callback.invoke(true)
            dialogLayout.dismiss()
        }
    }
    dialogLayout.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialogLayout.show()
}

fun Context.customAlertDialog(
    @DrawableRes topIconResId: Int? = null,
    title: String? = null,
    @ColorRes titleTextColor: Int = R.color.colorPrimaryDark,
    message: String? = null,
    @ColorRes messageTextColor: Int = R.color.greyDark,
    positiveButton: String? = null,
    negativeButton: String? = null,
    @ColorRes positiveButtonTextColor: Int = R.color.white,
    @ColorRes negativeButtonTextColor: Int = R.color.colorPrimary,
    cancelable: Boolean = true,
    positiveCallback: (View) -> Unit = {},
    negativeCallback: (View) -> Unit = {}
) {
    val builder = MaterialAlertDialogBuilder(this, R.style.Yap_App_MaterialAlertDialog_Rounded)
    val alertDialog = builder.create().apply {
            val binding =
                ConfirmAlertDialogBinding.inflate(LayoutInflater.from(this@customAlertDialog))
            setView(binding.root)
            if (topIconResId != null) {
                binding.ivTopIcon.setImageResource(topIconResId)
            } else binding.ivTopIcon.visibility = View.GONE
            if (title.isNullOrBlank().not()) {
                binding.tvDialogTitle.text = title
                binding.tvDialogTitle.setTextColor(getColor(titleTextColor))
            } else binding.tvDialogTitle.visibility = View.GONE
            if (message.isNullOrBlank().not()) {
                binding.tvMessage.text = message
                binding.tvMessage.setTextColor(getColor(messageTextColor))
            } else binding.tvMessage.visibility = View.GONE

            if (positiveButton.isNullOrBlank().not()) {
                binding.btnNext.text = positiveButton
                binding.btnNext.setTextColor(getColor(positiveButtonTextColor))
                binding.btnNext.setOnClick { positiveCallback.invoke(it)
                dismiss()}

            } else binding.btnNext.visibility = View.GONE
            if (negativeButton.isNullOrBlank().not()) {
                binding.btnClose.text = negativeButton
                binding.btnClose.setTextColor(getColor(negativeButtonTextColor))
                binding.btnClose.setOnClick {
                    negativeCallback.invoke(it)
                    dismiss()
                }
            } else binding.btnClose.visibility = View.GONE
        }
    alertDialog.setCancelable(cancelable)
    alertDialog.show()
}
