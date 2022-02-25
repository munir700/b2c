package co.yap.yapcore.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import co.yap.yapcore.R

fun Context.showTwoOptionsAlertDialog(
    dialogTitle: String? = null,
    dialogDescription: String? = null,
    leftButtonText: String = "OK",
    rightButtonText: String = "Cancel",
    callback: () -> Unit = {}
): android.app.AlertDialog {
    val builder = android.app.AlertDialog.Builder(this)
    var alertDialog: android.app.AlertDialog? = null
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val dialogLayout: View =
        inflater.inflate(R.layout.two_options_alert_dialog, null)
    val dTitle = dialogLayout.findViewById<TextView>(R.id.tvDialogTitle)
    dTitle.text = dialogTitle
    val dDescription = dialogLayout.findViewById<TextView>(R.id.tvDialogDescription)
    dDescription.text = dialogDescription
    val cancel = dialogLayout.findViewById<TextView>(R.id.tvButtonNegative)
    val ok = dialogLayout.findViewById<TextView>(R.id.tvButtonPositive)
    ok.text = rightButtonText
    cancel.text = leftButtonText
    cancel.setOnClickListener {
        alertDialog?.dismiss()
    }
    dTitle.visibility = if (dialogTitle == null) View.GONE else View.VISIBLE
    dDescription.visibility = if (dialogDescription == null) View.GONE else View.VISIBLE

    ok.setOnClickListener {
        alertDialog?.dismiss()
        callback()
    }

    builder.setView(dialogLayout)
    builder.setCancelable(false)
    alertDialog = builder.create()

    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()
    return alertDialog
}