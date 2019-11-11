package co.yap.modules.dashboard.yapit.topup.main.carddetail

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class TopUpCardDetailState : BaseState(), ITopUpCardDetail.State {
    override val cardNickname: ObservableField<String> = ObservableField()
    override val cardNo: ObservableField<String> = ObservableField()
    override val cardType: ObservableField<String> = ObservableField()
    override val cardExpiry: ObservableField<String> = ObservableField()
    override val title: ObservableField<String> = ObservableField()
}