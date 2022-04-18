package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText

class EasyBankTransferWelcomeViewModel(application: Application) :
    AddMoneyBaseViewModel<IEasyBankTransferWelcome.State>(application),
    IEasyBankTransferWelcome.ViewModel {

    override val leanOnBoardModel: MutableLiveData<LeanOnBoardModel> = MutableLiveData()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IEasyBankTransferWelcome.State = EasyBankTransferWelcomeState()
    private val leanTechRepository: LeanTechRepository = LeanTechRepository

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

    override fun onboardUser() {
        launch {
            state.loading = true
            when (val response = leanTechRepository.onBoardUser()) {
                is RetroApiResponse.Success -> {
                    leanOnBoardModel.postValue(response.data.data)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    toast(context, response.error.message)
                }
            }
        }
    }

}