package co.yap.household.onboarding.main

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IOnBoardingHouseHold {
    interface View{
        fun onVerificationComplete(isCompleted:Boolean)
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
        var totalProgress: MutableLiveData<Int>
        var currentProgress: MutableLiveData<Int>
    }
}