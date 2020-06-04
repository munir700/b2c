package co.yap.sendmoney.helper

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import co.yap.sendmoney.R
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.CoreButton
import co.yap.yapcore.interfaces.OnItemClickListener

@SuppressLint("StaticFieldLeak")
object SendMoneyUtils {

    fun confirmationDialog(
        context: Context,
        title: String,
        message: String,
        positiveButton: String,
        negitiveButton: String,
        itemClick: OnItemClickListener
    ) {
        androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle(title).setMessage(message)
            .setPositiveButton(
                positiveButton
            ) { _, _ ->
                itemClick.onItemClick(View(context), true, 0)
            }
            .setNegativeButton(
                negitiveButton
            ) { _, _ ->
                itemClick.onItemClick(View(context), false, 0)
            }
            .show()
    }


    fun showUnverifiedPopup(
        context: Context,
        title: String,
        message: String,
        positiveButton: String,
        negitiveButton: String,
        itemClick: OnItemClickListener
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_add_beneficiary_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = dialog.findViewById<TextView>(R.id.tvDescription)
        tvTitle.text = title




        tvDescription.text = message
 
        dialog.findViewById<CoreButton>(R.id.btnSendMoneyNow).setOnClickListener {
            itemClick.onItemClick(View(context), true, 0)

        }
        dialog.findViewById<TextView>(R.id.btnLater).setOnClickListener {
            itemClick.onItemClick(View(context), true, 0)
        }
        dialog.show()
    }

}