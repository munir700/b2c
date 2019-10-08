package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import android.view.View.GONE
import android.view.View.VISIBLE
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.states.ProfileStates
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import java.text.SimpleDateFormat
import java.util.*


class ProfileSettingsViewModel(application: Application) :
    MoreBaseViewModel<IProfile.State>(application), IProfile.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var showExpiredBadge: Boolean = false
    override lateinit var data: GetMoreDocumentsResponse
    override val repository: CustomersRepository = CustomersRepository

    override val state: ProfileStates =
        ProfileStates()

    override val clickEvent: SingleClickEvent = SingleClickEvent()


    override fun handlePressOnPersonalDetail(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnPrivacy(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnPasscode(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAppNotification(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnTermsAndConditions(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnInstagram(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnTwitter(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnFaceBook(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnLogOut(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnAddNewPhoto(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnPhoto(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnBackButton() {
    }


    override fun onResume() {
        super.onResume()

        setToolBarTitle(getString(Strings.screen_profile_settings_display_text_title))


    }

    override fun onCreate() {
        super.onCreate()
        requestProfileDocumentsInformation()

    }

    override fun requestProfileDocumentsInformation() {

        launch {
            state.loading = true
            when (val response = repository.getMoreDocumentsByType("EMIRATES_ID")) {

                is RetroApiResponse.Success -> {
                    data = response.data

                    if (!data.data.dateExpiry.isNullOrEmpty()) {
                        getExpiryDate(data.data.dateExpiry)
                    }
                }

                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    fun getExpiryDate(expiryDateString: String) {

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val expireyDate = simpleDateFormat.parse(expiryDateString)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)

        val prevDay = simpleDateFormat.format(cal.time)
        val previousDayDate = simpleDateFormat.parse(prevDay)

        if (expireyDate > previousDayDate) {
            state.errorBadgeVisibility = GONE
            showExpiredBadge = false
        } else {
            state.errorBadgeVisibility = VISIBLE
            showExpiredBadge = true
        }
    }
}