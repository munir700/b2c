package co.yap.sendmoney.helper

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.TextView
import co.yap.sendmoney.R
import co.yap.widgets.CoreButton
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

@SuppressLint("StaticFieldLeak")
object SendMoneyUtils {

    fun confirmationDialog(
        context: Context,
        title: String,
        message: String,
        positiveButton: String,
        negitiveButton: String,
        itemClick: OnItemClickListener, showCustomDialogue: Boolean = false

    ) {
        if (showCustomDialogue!! == true) {
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
        } else {
            Utils.confirmationDialog(
                context,
                title,
                message,
                positiveButton,
                negitiveButton,itemClick
            )
        }

    }

}