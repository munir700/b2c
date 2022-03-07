package co.yap.yapcore.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import co.yap.yapcore.R

fun Context.showTwoOptionsAlertDialog(
    dialogTitle: String? = null,
    dialogDescription: String? = null,
    okButtonText: String = "OK"
): android.app.AlertDialog {
    val builder = android.app.AlertDialog.Builder(this)
    var alertDialog: android.app.AlertDialog? = null
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val dialogLayout: View =
        inflater.inflate(R.layout.single_button_alert_dialog, null)
    val dTitle = dialogLayout.findViewById<TextView>(R.id.tvDialogTitle)
    dTitle.text = dialogTitle
    val dDescription = dialogLayout.findViewById<TextView>(R.id.tvDialogDescription)
    dDescription.text = dialogDescription
    val ok = dialogLayout.findViewById<TextView>(R.id.tvButtonPositive)
    ok.text = okButtonText
    ok.setOnClickListener {
        alertDialog?.dismiss()
    }
    dTitle.visibility = if (dialogTitle == null) View.GONE else View.VISIBLE
    dDescription.visibility = if (dialogDescription == null) View.GONE else View.VISIBLE

    builder.setView(dialogLayout)
    builder.setCancelable(false)
    alertDialog = builder.create()

    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()
    return alertDialog
}