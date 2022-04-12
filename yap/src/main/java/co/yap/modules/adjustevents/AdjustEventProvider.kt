package co.yap.modules.adjustevents

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.yap.core.analytics.IAdjustEvent

class AdjustEventProvider : IAdjustEvent {

    override fun logEvent(eventToken: String?) {
        val attribution = Adjust.getAttribution()
        val adjustEvent = AdjustEvent(eventToken)
//        adjustEvent.setCallbackId(customerID)
//        adjustEvent.addCallbackParameter("account_id", customerID)
        Adjust.trackEvent(adjustEvent)
    }
}