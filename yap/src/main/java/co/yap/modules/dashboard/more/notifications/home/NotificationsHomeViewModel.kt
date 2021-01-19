package co.yap.modules.dashboard.more.notifications.home

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.MsCustomerNotificationsRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.firebase.getFCMToken
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.SessionManager

class NotificationsHomeViewModel(application: Application) :
    BaseViewModel<INotificationsHome.State>(application),
    INotificationsHome.ViewModel {
    override val state = NotificationsHomeState()
    override val mNotificationsHomeAdapter: ObservableField<NotificationsHomeAdapter>? =
        ObservableField()
    override val repository: CustomersRepository = CustomersRepository

    override fun onCreate() {
        super.onCreate()
        getNotification()
        getFcmNotifications()

    }

    override fun getNotification() {
        SessionManager.user?.let { account ->
            SessionManager.card.value?.let { card ->
                state.mNotifications?.value =
                    NotificationHelper.getNotifications(
                        account,
                        card, context
                    )
                mNotificationsHomeAdapter?.get()?.setData(
                    state.mNotifications?.value ?: arrayListOf()
                )
            }
        }

    }

    override fun getFcmNotifications() {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        val deviceId: String? = sharedPreferenceManager.getValueString(Constants.KEY_APP_UUID)
           getFCMToken { token ->
            launch {
                state.loading = true
                when (val response = repository.getMsCustomerNotification(
                    msCustomerNotifications = MsCustomerNotificationsRequest(token,deviceId))) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        mNotificationsHomeAdapter?.get()?.setData(
                            state.mNotifications?.value ?: arrayListOf()
                        )
                    }
                    is RetroApiResponse.Error -> {
                        showToast(response.error.message)
                        state.loading = false
                    }
                }
            }
        }


    }

    override fun updateFcmNotifications(notifId : String, isRead : Boolean) {
        launch {
            state.loading = true
            when (val response = repository.updateReadNotifications(
               notificationId = notifId,isRead = isRead)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    mNotificationsHomeAdapter?.get()?.setData(
                        state.mNotifications?.value ?: arrayListOf()
                    )
                }
                is RetroApiResponse.Error -> {
                    showToast(response.error.message)
                    state.loading = false
                }
            }
        }
    }

    override fun deleteFcmNotifications(notifId : String) {
        launch {
            state.loading = true
            when (val response = repository.deleteMsCustomerNotification(
                notificationId = notifId)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    mNotificationsHomeAdapter?.get()?.setData(
                        state.mNotifications?.value ?: arrayListOf()
                    )
                }
                is RetroApiResponse.Error -> {
                    showToast(response.error.message)
                    state.loading = false
                }
            }
        }
    }

    //    fun loadNotifications() {
//        launch {
//            state.loading = true
//            val notifications = ArrayList<Notification>()
//            Leanplum.getInbox().allMessages()
//            Leanplum.getInbox().allMessages().forEach {
//                notifications.add(
//                    Notification(
//                        it.title,
//                        if (null != it.data && it.data.has("description")) it.data.getString("description") else "",
//                        "",
//                        "",
//                        it.imageFilePath,
//                        "",
//                        it.deliveryTimestamp.toString(),
//                        it.isRead,
//                        it.messageId
//                    )
//                )
//            }
//            state.hasRecords.set(!notifications.isNullOrEmpty())
//            adapter.setList(notifications)
//            state.loading = false
//        }
//    }
}