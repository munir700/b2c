package co.yap.modules.dashboard.more.home.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.more.home.interfaces.IMoreHome
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.modules.dashboard.more.home.states.MoreState
import co.yap.modules.dashboard.more.main.viewmodels.MoreBaseViewModel
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.NotificationsRepository
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.maskIbanNumber
import co.yap.yapcore.helpers.extentions.parseToInt
import co.yap.yapcore.managers.SessionManager
import com.leanplum.Leanplum

class MoreHomeViewModel(application: Application) :
    MoreBaseViewModel<IMoreHome.State>(application), IMoreHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: MoreState = MoreState()
    override var badgeCount: ObservableField<String> = ObservableField("")
    override var hasBadge: ObservableField<Boolean> = ObservableField(false)
    override val list: MutableList<CoreBottomSheetData> = mutableListOf()

    private val _notificationCountData = MutableLiveData<Int?>()
    override val notificationCountData: LiveData<Int?> = _notificationCountData

    override fun onResume() {
        super.onResume()
        setPicture()
    }

    private fun setPicture() {
        SessionManager.user?.currentCustomer?.getPicture()?.let {
            state.image.set(it)
        }
        SessionManager.user?.currentCustomer?.getFullName()?.let {
            state.initials.set(Utils.shortName(it))
        }
    }

    override fun handlePressOnYAPforYou(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getMoreOptions(): MutableList<MoreOption> {
        val list = mutableListOf<MoreOption>()
        list.add(
            MoreOption(
                Constants.MORE_INVITE_FRIEND,
                Translator.getString(context, Strings.screen_more_invite_a_friend),
                R.drawable.ic_invite,
                ContextCompat.getColor(context, R.color.colorPrimaryAlt),
                false,
                0
            )
        )
        //colorSecondaryGreen
        list.add(
            MoreOption(
                Constants.MORE_LOCATE_ATM,
                Translator.getString(context, Strings.screen_more_atm_cdm),
                R.drawable.atm,
                ContextCompat.getColor(context, R.color.colorSecondaryGreen),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                Constants.MORE_YAP_FOR_YOU,
                Translator.getString(context, Strings.screen_yap_for_you_display_text_title),
                R.drawable.yap_for_you,
                ContextCompat.getColor(context, R.color.transparent),
                false,
                Leanplum.getInbox().unreadCount()
            )
        )
        list.add(
            MoreOption(
                Constants.MORE_HELP_SUPPORT,
                Translator.getString(context, Strings.screen_more_help_and_support),
                R.drawable.help_and_support,
                ContextCompat.getColor(context, R.color.colorSecondaryBlue),
                false,
                0
            )
        )
        /*list.add(
            MoreOption(
                Constants.MORE_SUBSCRIPTION,
                Translator.getString(context, Strings.screen_more_subscription),
                R.drawable.subscription,
                ContextCompat.getColor(context, R.color.colorSecondaryBlue),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                Constants.MORE_GIFTS,
                Translator.getString(context, Strings.screen_more_gifts),
                R.drawable.more_gift,
                ContextCompat.getColor(context, R.color.colorSecondaryBlue),
                false,
                0

            )
        )*/
        return list
    }


    override fun getTransactionsNotificationsCount() {
        launch {
            val notificationCount =
                when (val response = NotificationsRepository.getTransactionsNotificationsCount()) {
                    is RetroApiResponse.Success -> response.data.data?.parseToInt()
                    is RetroApiResponse.Error -> 0
                }
            _notificationCountData.value = notificationCount
        }
    }

    override fun loadBottomSheetData(): MutableList<CoreBottomSheetData> {
        list.clear()
        list.add(
            CoreBottomSheetData(
                content = Translator.getString(
                    getApplication(),
                    Strings.screen_b2c_eid_info_review_display_text_name_heading
                ),
                subContent = SessionManager.user?.currentCustomer?.getFullName(),
                isSelected = false
            )
        )
        list.add(
            CoreBottomSheetData(
                content = Translator.getString(
                    getApplication(),
                    Strings.screen_add_topup_bank_iban_label_text
                ),
                subContent = SessionManager.user?.iban?.maskIbanNumber() ?: "",
                isSelected = true
            )
        )
        list.add(
            CoreBottomSheetData(
                content = Translator.getString(
                    getApplication(),
                    Strings.screen_more_detail_display_text_account
                ),
                subContent = SessionManager.user?.accountNo ?: "",
                isSelected = true
            )
        )
        list.add(
            CoreBottomSheetData(
                content = Translator.getString(
                    getApplication(),
                    Strings.screen_more_detail_display_text_bank
                ),
                subContent = SessionManager.user?.bank?.name ?: "",
                isSelected = false
            )
        )

        list.add(
            CoreBottomSheetData(
                content = Translator.getString(
                    getApplication(),
                    Strings.screen_add_topup_bank_bic_label_text
                ),
                subContent = SessionManager.user?.bank?.swiftCode ?: "",
                isSelected = true
            )
        )
        list.add(
            CoreBottomSheetData(
                content = Translator.getString(
                    getApplication(),
                    Strings.screen_meeting_location_input_text_address_title
                ),
                subContent = SessionManager.user?.bank?.address ?: "",
                isSelected = true
            )
        )
        return list
    }
}