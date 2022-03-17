package co.yap.modules.adjustevents

import co.yap.modules.adjustevents.AdjustEventProvider
import com.yap.core.analytics.AnalyticsEvent
import com.yap.core.analytics.IAdjustEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsEventsHandler @Inject constructor() : AnalyticsEvent {
    override val adjust: IAdjustEvent

    init {
        adjust = AdjustEventProvider()
    }
}