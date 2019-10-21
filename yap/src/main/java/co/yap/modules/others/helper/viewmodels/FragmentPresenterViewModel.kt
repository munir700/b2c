package co.yap.modules.others.helper.viewmodels

import android.app.Application
import co.yap.modules.others.helper.interfaces.IFragmentPresenter
import co.yap.modules.others.helper.states.FragmentPresenterState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class FragmentPresenterViewModel(application: Application) :
    BaseViewModel<IFragmentPresenter.State>(application), IFragmentPresenter.ViewModel {


    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: FragmentPresenterState = FragmentPresenterState()


}