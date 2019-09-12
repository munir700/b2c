package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.models.CardInfoModel
import co.yap.translation.Strings

class AddFundsViewModel(application: Application) : FundActionsViewModel(application) {

//    lateinit var cardInfoModel:CardInfoModel

    override fun onCreate() {
        super.onCreate()
        state.toolBarHeader=getString(Strings.screen_add_funds_display_text_add_funds)
        state.cardName=getString(Strings.screen_add_card_display_text_spare_card)
        state.cardNumber="4040 3318 **** 3456"
    }

}