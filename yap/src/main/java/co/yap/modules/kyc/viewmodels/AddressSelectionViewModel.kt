package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.states.AddressSelectionState
import co.yap.yapcore.BaseViewModel

class AddressSelectionViewModel(application: Application) : BaseViewModel<IAddressSelection.State>(application),
    IAddressSelection.ViewModel {

    override val state: AddressSelectionState = AddressSelectionState(application)

//    override val nextButtonPressEvent: SingleLiveEvent<Boolean>
//        get() =
    override fun handlePressOnNext() {
        state.headingTitle="test"
    }

    override fun handlePressOnSelectLocation() {
        state.headingTitle="test"
    }

      fun handlePressOnChangeLocation() {
        state.locationBtnText=getString(R.string.screen_meeting_location_button_change_location)
     }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    if (state.valid) {
//                        handlePressOnNext()
//                    }
                }
                return false
            }
        }
    }

//    val clickListener: MutableLiveData<Int> = MutableLiveData()

//    override fun handlePressOnNextButton(id: Int) {
//        clickListener.value = id
//    }
//
//    override fun handlePressOnChangeLocation(id: Int) {
//        clickListener.value = id
//    }
//
//    override fun handlePressOnSkipButton(id: Int) {
//        clickListener.value = id
//    }
}