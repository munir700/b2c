package co.yap.modules.dashboard.yapit.topup.main.carddetail

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.models.CardInfo
import co.yap.yapcore.BaseState

class TopUpCardDetailState : BaseState(), ITopUpCardDetail.State {
    override val title: ObservableField<String> = ObservableField()
    override val isCardDeleted: MutableLiveData<Boolean> = MutableLiveData()
    override val cardInfo: ObservableField<CardInfo> = ObservableField()
}