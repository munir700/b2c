package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.states.ProfileStates
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.documents.Data
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import java.text.SimpleDateFormat
import java.util.*


class ProfileSettingsViewModel(application: Application) :
    MoreBaseViewModel<IProfile.State>(application), IProfile.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override lateinit var data: Data
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

        val stringVal: String = "2020-10-09"
        getExpiryDate(stringVal)
//        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
//        val dateExpiry = SimpleDateFormat("yyyy-MM-dd").parse(stringVal)
//        println(dateExpiry.time)
//        println(dateExpiry)
//        println(currentDateTimeString)


//        val expiryDateString: String = "2017-07-25"
//
//        val currDate = SimpleDateFormat("yyyy-mm-dd").format(Calendar.getInstance().time)
//        println(currDate)
//
//        var sdf = SimpleDateFormat("yyyy-mm-dd")
//        val date = sdf.parse(expiryDateString)
//
//        val outputString = sdf.format(date)
//        println(outputString)

    }

    override fun requestProfileDocumentsInformation() {

        launch {
            state.loading = true
            when (val response = repository.getMoreDocumentsByType("EMIRATES_ID")) {

                is RetroApiResponse.Success -> {
                    data = response.data.data

                    if (!data.dateExpiry.isNullOrEmpty()) {
//                        getExpiryDate(data.dateExpiry)
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

        var sdf = SimpleDateFormat("yyyy-MM-dd")
        val expireyDate = sdf.parse(expiryDateString)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)

        val prevDay = sdf.format(cal.time)
        val previousDayDate = sdf.parse(prevDay)

        if (expireyDate > previousDayDate) {
            // hide red icon
            state.errorBadgeVisibility = GONE

            Log.i(
                "checkz",
                "isNotExpired " + previousDayDate + " prevdate , expireyDate " + expireyDate
            )

        } else {
            state.errorBadgeVisibility = VISIBLE

            // show red icon
            Log.i(
                "checkz",
                "hasExpired " + previousDayDate + " prevdate , expireyDate " + expireyDate
            )

        }
    }

}