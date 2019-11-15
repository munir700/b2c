package co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import co.yap.R
import kotlinx.android.synthetic.main.dialog_add_topup_card.*

class AddTopUpCardDialog(context: Context) : Dialog(context) {
    private var onProceedListener: OnProceedListener? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setContentView(R.layout.dialog_add_topup_card)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setListeners()
    }

    private fun setListeners() {
        btnLater.setOnClickListener {
            onProceedListener?.onProceed(it)
            dismiss()
        }
        btnTopUpNow.setOnClickListener {
            onProceedListener?.onProceed(it)
            dismiss()
        }
    }


    interface OnProceedListener {
        fun onProceed(view: View)
    }

    companion object {
        @JvmStatic

        fun newInstance(callback: OnProceedListener?, context: Context): AddTopUpCardDialog {
            val dialog = AddTopUpCardDialog(context)
            dialog.onProceedListener = callback
            //  dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
            return dialog
        }
    }
}

