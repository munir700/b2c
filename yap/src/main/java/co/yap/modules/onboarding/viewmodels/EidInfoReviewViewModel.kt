package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.yapcore.BaseViewModel

class EidInfoReviewViewModel(application: Application) : BaseViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel {
    override val state: EidInfoReviewState = EidInfoReviewState()
}