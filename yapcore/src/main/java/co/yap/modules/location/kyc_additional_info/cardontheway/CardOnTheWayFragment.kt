package co.yap.modules.location.kyc_additional_info.cardontheway

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants

class CardOnTheWayFragment : BaseBindingFragment<ICardOnTheWay.ViewModel>(),
    ICardOnTheWay.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_card_on_the_way

    override val viewModel: CardOnTheWayViewModel
        get() = ViewModelProviders.of(this).get(CardOnTheWayViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickListener)
    }

    private val clickListener = Observer<Int> {
        when (it) {
            R.id.btnGoToDashboard -> setIntentResult()
        }
    }

    private fun setIntentResult() {
        val intent = Intent()
        intent.putExtra(Constants.ADDRESS_SUCCESS, true)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

}