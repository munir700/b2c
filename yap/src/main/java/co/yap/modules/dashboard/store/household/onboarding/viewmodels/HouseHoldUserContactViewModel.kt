package co.yap.modules.dashboard.store.household.onboarding.viewmodels

import android.app.Application
import android.widget.EditText
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserContact
import co.yap.modules.dashboard.store.household.onboarding.states.HouseHoldUserContactState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class HouseHoldUserContactViewModel(application: Application) :
    BaseOnboardingViewModel<IHouseHoldUserContact.State>(application),
    IHouseHoldUserContact.ViewModel/*,
    IRepositoryHolder<CardsRepository>*/ {

    override val state: HouseHoldUserContactState = HouseHoldUserContactState(application)

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnAdd(id: Int) {
        parentViewModel?.userMobileNo = state.etMobileNumberConfirmMobile?.text.toString()
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton() {

    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_yap_house_hold_user_info_display_text_title))
    }

    override fun getCcp(editText: EditText) {
        editText.requestFocus()
        state.etMobileNumber = editText
        state.etMobileNumber!!.requestFocus()
    }

    override fun getConfirmCcp(editText: EditText) {
        state.etMobileNumberConfirmMobile = editText
    }
}
