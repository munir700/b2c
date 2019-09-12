package co.yap.modules.cards.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.cards.interfaces.IPrimaryPaymentCard
import co.yap.modules.cards.viewmodels.PrimaryPaymentCardViewModel
import co.yap.yapcore.BaseBindingActivity

class PrimaryPaymentCardActivity : BaseBindingActivity<IPrimaryPaymentCard.ViewModel>(),
    IPrimaryPaymentCard.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_primary_payment_card

    override val viewModel: IPrimaryPaymentCard.ViewModel
        get() = ViewModelProviders.of(this).get(PrimaryPaymentCardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
