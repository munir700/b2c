package co.yap.sendmoney.currencyPicker.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import co.yap.sendmoney.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.sendmoney.BR
import co.yap.sendmoney.currencyPicker.interfaces.IMultiCurrencyPickerDialog
import co.yap.sendmoney.currencyPicker.viewmodel.MultiCurrencyPickerVM

class ActivityMultiCurrencyPickerDialog :
    BaseBindingActivity<IMultiCurrencyPickerDialog.ViewModel>(), IFragmentHolder {

    companion object {
        private const val BUNDLE_DATA_CURRENCY = "bundle_data_currency"
        const val IS_DIALOG_POP_UP = "dialog_checker"
        const val LIST_OF_CURRENCIES = "currency_list"
        fun getIntent(context: Context, bundle: Bundle = Bundle()): Intent {
            val intent = Intent(context, ActivityMultiCurrencyPickerDialog::class.java)
            intent.putExtra(BUNDLE_DATA_CURRENCY, bundle)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel


    override fun getLayoutId(): Int =
        R.layout.activity_multi_currency_picker_dialog


    override val viewModel: IMultiCurrencyPickerDialog.ViewModel
        get() = ViewModelProviders.of(this).get(MultiCurrencyPickerVM::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        setLayoutParams()
        super.onCreate(savedInstanceState)
        setFinishOnTouchOutside(true)
        findNavController(R.id.main_multi_currency_picker_host_fragment).setGraph(
            R.navigation.navigation_currency_picker_fragment,
            intent?.getBundleExtra(BUNDLE_DATA_CURRENCY)
        )

    }

    private fun setLayoutParams() {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window.attributes)
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.54).toInt()
        layoutParams.width = width
        layoutParams.height = height
        layoutParams.gravity = Gravity.CENTER
        window.attributes = layoutParams

    }

}