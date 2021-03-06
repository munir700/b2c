package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardname

import android.app.Application
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentChildViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.loadCardImage

class AddVirtualCardNameViewModel(application: Application) :
    AddPaymentChildViewModel<IAddVirtualCardName.State>(application),
    IAddVirtualCardName.ViewModel {
    override val state: AddVirtualCardNameState =
        AddVirtualCardNameState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        setToolBarTitle(getString(Strings.screen_add_virtual_spare_card_tool_bar_title_choose_name))
        toggleToolBarVisibility(View.VISIBLE)
        if(parentViewModel?.selectedCardName?.get()?.isNotEmpty() == true){
            state.cardName.value = parentViewModel?.selectedCardName?.get()
            state.enabelCoreButton = true
        }
    }

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun observeCardNameLength(str: String): Boolean =
        (str.isNotEmpty() && str.length <= 26).let {
            state.enabelCoreButton = it
            it
        }

    override fun setCardImage(imgCard: AppCompatImageView) {
        imgCard.loadCardImage(parentViewModel?.selectedVirtualCard?.frontSideDesignImage)
    }

}
