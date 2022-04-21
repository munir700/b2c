package co.yap.modules.dashboard.yapit.topup.addtopupcard

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import co.yap.R
import co.yap.widgets.CoreButton

class AddTopUpCardDialog(context: Context) : Dialog(context) {
    private var onProceedListener: OnProceedListener? = null


    private fun setListeners() {

        findViewById<TextView>(R.id.btnLater).setOnClickListener {
            onProceedListener?.onProceed(it.id)
            dismiss()
        }
        findViewById<CoreButton>(R.id.done).setOnClickListener {
            onProceedListener?.onProceed(it.id)
            dismiss()
        }
    }


    interface OnProceedListener {
        fun onProceed(id: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(callback: OnProceedListener?, context: Context): AddTopUpCardDialog {
            val dialog =
                AddTopUpCardDialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_add_topup_card)
            dialog.onProceedListener = callback
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setListeners()
            return dialog
        }
    }
}

