package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import co.yap.modules.dashboard.store.cardplans.CardPlansAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.states.CardPlansState
import co.yap.yapcore.helpers.Utils

class CardPlansViewModel(application: Application) :
    CardPlansBaseViewModel<ICardPlans.State>(application), ICardPlans.ViewModel {
    override val state: CardPlansState = CardPlansState()
    override var cardAdapter: CardPlansAdapter = CardPlansAdapter(mutableListOf(), null)
    override fun setViewDimensions(
        percent: Int,
        view: View
    ): ConstraintLayout.LayoutParams {
        val dimensions: Int = Utils.getDimensionInPercent(context, false, percent)
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.height = dimensions
        return params
    }

    override fun onCreate() {
        super.onCreate()
        getCards()?.let { cardAdapter.setData(it) }
    }
}
