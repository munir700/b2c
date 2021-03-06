package co.yap.modules.kyc.cardontheway

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.kyc.fragments.KYCChildFragment
import co.yap.translation.Strings
import co.yap.yapcore.helpers.infoDialog

class CardOnTheWayFragment : KYCChildFragment<ICardOnTheWay.ViewModel>(), ICardOnTheWay.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_card_on_the_way

    override val viewModel: CardOnTheWayViewModel
        get() = ViewModelProviders.of(this).get(CardOnTheWayViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*uncomment below lines to show Why do we ask you about your monthly salary? dialog info*/
        /*requireContext().infoDialog(
            message = getString(Strings.screen_employment_information_dialog_display_text_subheading),
            title = getString(Strings.screen_employment_information_dialog_display_text_heading),
            buttonText = getString(Strings.screen_employment_information_dialog_button_text_close)
        )*/

        /*uncomment below lines to show For those people who still use cash, how much cash will you add to YAP per month? dialog info*/

        /*requireContext().infoDialog(
           message = getString(Strings.screen_employment_information_cash_dialog_display_text_subheading),
           title = getString(Strings.screen_employment_information_cash_dialog_display_text_heading),
           buttonText = getString(Strings.screen_employment_information_dialog_button_text_close)
       )*/

        /*uncomment the below lines to show list in bottom sheet*/
        /*val coreBottomSheet = CoreBottomSheet(
            object :
                OnItemClickListener {
                override fun onItemClick(view: View, data: Any, pos: Int) {
                    (data as? CoreBottomSheet)?.dismiss()
                }
            },
            bottomSheetItems = viewModel.state.bottomSheetItems.get() ?: arrayListOf(),
            headingLabel = "Select which statement describes your situation best:",
            viewType = Constants.VIEW_WITHOUT_FLAG
        )
        coreBottomSheet.show(this.childFragmentManager, "")*/
    }
}