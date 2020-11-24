package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentChildViewModel
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import java.util.*

class AddVirtualCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddVirtualCard.State>(application), IAddVirtualCard.ViewModel {
    override var adapter: AddVirtualCardAdapter = AddVirtualCardAdapter(mutableListOf())
    override val state: AddVirtualCardState = AddVirtualCardState()
    override fun onCreate() {
        super.onCreate()
        adapter = AddVirtualCardAdapter(getCardThemesOption())
    }

    private fun getCardThemesOption(): MutableList<VirtualCardModel> {
        val cards: MutableList<VirtualCardModel> = mutableListOf()
        for (x in 0 until 5) {
            cards.add(
                VirtualCardModel(
                    "2019-09-19",
                    "https://s3-eu-west-1.amazonaws.com/dev-a-yap-documents-public/1568890204540_Error_Message.png",
                    "3567b3e6-0836-4316-84ee-0f02fa1177ca",
                    "qq",
                    "qq",
                    "",
                    "ACTIVE",
                    String.format("#%06x", Random().nextInt(0xffffff + 1)),
                    "cd",
                    "aq",
                    true
                )
            )
        }
        return cards
    }
}
