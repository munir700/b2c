package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import android.net.Uri
import android.widget.VideoView
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.adaptors.CardPlansAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.states.CardPlansState
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

class CardPlansViewModel(application: Application) :
    CardPlansBaseViewModel<ICardPlans.State>(application), ICardPlans.ViewModel {
    override val state: CardPlansState = CardPlansState()
    override var cardAdapter: CardPlansAdapter = CardPlansAdapter(mutableListOf(), null)
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        getCards()?.let { cardAdapter.setData(it) }
    }

    override fun iniVideoView(video: VideoView) {
        video.layoutParams =
            parentViewModel?.setViewDimensions(32, video)
        video.setVideoURI(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.video_all_card_plans))
        video.start()
        video.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            video.start()
        }
    }
}
