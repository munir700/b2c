package co.yap.modules.dashboard.yapit.topup.main.carddetail

import android.app.Application
import co.yap.R
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TopUpCardDetailViewModel(application: Application) :
    BaseViewModel<ITopUpCardDetail.State>(application),
    ITopUpCardDetail.ViewModel {
    override val state: ITopUpCardDetail.State = TopUpCardDetailState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    init {
        state.title.set(getString(R.string.screen_topup_card_detail_display_text_title))
    }


    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onRemoveCard() {
        // call the api to remove the card
    }


}