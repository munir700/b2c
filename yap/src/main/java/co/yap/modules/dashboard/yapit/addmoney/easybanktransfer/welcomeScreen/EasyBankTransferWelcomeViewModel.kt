package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText

class EasyBankTransferWelcomeViewModel(application: Application) :
    AddMoneyBaseViewModel<IEasyBankTransferWelcome.State>(application),
    IEasyBankTransferWelcome.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setDataFormat() {
        state.welcomeText.set(
            context.resources.getText(
                getString(Strings.screen_lean_welcome_screen_connect_one_of_your_existing_bank),
                context.color(
                    R.color.colorPrimaryDark,
                    "instantly"
                ),
                context.color(
                    R.color.colorPrimaryDark,
                    "zero fees!"
                )
            )
        )
    }

    override val state: IEasyBankTransferWelcome.State = EasyBankTransferWelcomeState()
}