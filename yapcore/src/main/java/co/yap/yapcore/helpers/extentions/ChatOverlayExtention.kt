package co.yap.yapcore.helpers.extentions

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import co.yap.networking.authentication.AuthRepository
import co.yap.widgets.CounterFloatingActionButton
import co.yap.yapcore.R
import co.yap.yapcore.managers.SessionManager
import com.leanplum.Leanplum
import com.liveperson.infra.*
import com.liveperson.infra.callbacks.InitLivePersonCallBack
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.model.ConsumerProfile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val BRAND_ID: String = "17038977"
private val appInstallId = SessionManager.user?.uuid

fun Activity.initializeChatOverLayButton(unreadCount: Int, fab: CounterFloatingActionButton) {
    if (unreadCount <= 0) {
        fab.visibility = View.GONE
        (window.decorView as FrameLayout).findViewById<FrameLayout>(android.R.id.content)
            .removeView(fab)
        return
    }

    val param = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )
    param.setMargins(
        dimen(R.dimen.margin_medium),
        dimen(R.dimen.margin_medium),
        dimen(R.dimen.margin_medium),
        dimen(R.dimen.margin_btn_side_paddings_xl)
    )
    param.gravity = Gravity.END or Gravity.BOTTOM
    GlobalScope.launch {
        delay(3000)
        fab.visibility = View.VISIBLE
        (window.decorView as FrameLayout).findViewById<FrameLayout>(android.R.id.content)
            .removeView(fab)
        (window.decorView as FrameLayout).findViewById<FrameLayout>(android.R.id.content)
            .addView(fab, param)
    }
    fab.count = unreadCount
    fab.setOnClickListener { chatSetup() }
}

fun Activity.overLayButtonVisibility(visibility: Int) {
    if (Leanplum.getInbox().unreadCount() > 0)
        (window.decorView as FrameLayout).findViewById<View>(R.id.faLiveChat)?.visibility =
            visibility
}

fun Activity.chatSetup() {
    SessionManager.user?.let {
//        val monitoringParams = MonitoringInitParams(appInstallId)
        LivePerson.initialize(
            this,
            InitLivePersonProperties(
                BRAND_ID, it.uuid,
                object : InitLivePersonCallBack {
                    override fun onInitSucceed() {
                        openChatActivity()
                    }

                    override fun onInitFailed(e: Exception) {
                        toast("Unable to open chat")
                    }
                })
        )
    }
}

private fun Activity.openChatActivity() {
    SessionManager.user?.currentCustomer?.let {
        val authParams = LPAuthenticationParams(LPAuthenticationParams.LPAuthenticationType.AUTH)
        authParams.hostAppJWT = AuthRepository.getJwtToken()
//        authParams.hostAppJWT = CookiesManager.jwtToken
        val params = ConversationViewParams(false)
            .setHistoryConversationsStateToDisplay(LPConversationsHistoryStateToDisplay.OPEN)
            .setReadOnlyMode(false)
        LivePerson.showConversation(this, authParams, params)
        val consumerProfile = ConsumerProfile.Builder()
            .setFirstName(it.firstName)
            .setLastName(it.lastName)
            .setPhoneNumber(it.getCompletePhone())
            .build()
        LivePerson.setUserProfile(consumerProfile)
    }
}

fun Activity.getCountUnreadMessage( fab: CounterFloatingActionButton) {
    LivePerson.getUnreadMessagesCount(
        appInstallId,
        object : ICallback<Int, java.lang.Exception> {
            override fun onSuccess(count: Int?) {
                initializeChatOverLayButton(count ?: 0, fab)
            }

            override fun onError(p0: java.lang.Exception?) {
            }

        }
    )
}