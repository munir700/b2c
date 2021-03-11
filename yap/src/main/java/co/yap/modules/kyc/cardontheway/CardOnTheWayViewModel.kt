package co.yap.modules.kyc.cardontheway

import android.app.Application
import co.yap.modules.kyc.viewmodels.KYCChildViewModel
import co.yap.networking.coreitems.CoreBottomSheetData

class CardOnTheWayViewModel(application: Application) :
    KYCChildViewModel<ICardOnTheWay.State>(application),
    ICardOnTheWay.ViewModel {
    override val state: ICardOnTheWay.State = CardOnTheWayState()

    override fun onCreate() {
        super.onCreate()
        val bottomSheetItems: MutableList<CoreBottomSheetData> = arrayListOf()
        bottomSheetItems.add(
            CoreBottomSheetData(
                -1,
                "",
                "I am a homemaker"
            )
        )
        bottomSheetItems.add(
            CoreBottomSheetData(
                -1,
                "",
                "I am retired"
            )
        )
        bottomSheetItems.add(
            CoreBottomSheetData(
                -1,
                "",
                "I am a pensioner"
            )
        )
        bottomSheetItems.add(
            CoreBottomSheetData(
                -1,
                "",
                "Other"
            )
        )
        state.bottomSheetItems.set(bottomSheetItems)
    }
}