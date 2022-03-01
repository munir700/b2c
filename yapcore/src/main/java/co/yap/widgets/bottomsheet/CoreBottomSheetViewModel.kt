package co.yap.widgets.bottomsheet

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CoreBottomSheetViewModel(application: Application) :
    BaseViewModel<ICoreBottomSheet.State>(application),
    ICoreBottomSheet.ViewModel {

    override val state: CoreBottomSheetState = CoreBottomSheetState()


    override var selectedViewsList: ArrayList<String> = arrayListOf()
}