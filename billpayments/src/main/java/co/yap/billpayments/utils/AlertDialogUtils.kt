package co.yap.billpayments.utils

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.chatSetup
import co.yap.yapcore.helpers.extentions.makeCall
import co.yap.yapcore.helpers.extentions.makeLinks
import co.yap.yapcore.managers.SessionManager

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
): android.app.AlertDialog {
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
    return alertDialog
}