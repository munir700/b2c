package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.R
import co.yap.modules.kyc.interfaces.IAddressSelection
import co.yap.modules.kyc.states.AddressSelectionState
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel

class AddressSelectionViewModel(application: Application) : BaseViewModel<IAddressSelection.State>(application),
    IAddressSelection.ViewModel {

    override val state: AddressSelectionState = AddressSelectionState(application)

    override fun handlePressOnNext() {
        onLocatioenSelected()
    }

    fun onLocatioenSelected() {
        state.headingTitle = Translator.getString(getApplication(), R.string.screen_meeting_location_display_text_title)
        state.subHeadingTitle =
            Translator.getString(getApplication(), R.string.screen_meeting_location_display_text_selected_subtitle)
        state.locationBtnText =
            Translator.getString(getApplication(), R.string.screen_meeting_location_button_change_location)
    }

    override fun handlePressOnSelectLocation() {
         onLocatioenSelected()
    }

    fun handlePressOnChangeLocation() {
        state.locationBtnText = getString(R.string.screen_meeting_location_button_change_location)
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

}