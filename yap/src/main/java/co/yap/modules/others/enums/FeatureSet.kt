package co.yap.modules.others.enums

import co.yap.modules.dashboard.cards.paymentcarddetail.activities.ChangeCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities.AddFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities.RemoveFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.forgotcardpin.activities.ForgotCardPinActivity
import co.yap.modules.dashboard.yapit.topup.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.sendmoney.fundtransfer.activities.BeneficiaryFundTransferActivity

enum class FeatureSet(val screenName: String) {
    DOMESTIC(BeneficiaryFundTransferActivity::class.java.name),
    UAEFTS(BeneficiaryFundTransferActivity::class.java.name),
    CBWSI(BeneficiaryFundTransferActivity::class.java.name),
    RMT(BeneficiaryFundTransferActivity::class.java.name),
    SWIFT(BeneficiaryFundTransferActivity::class.java.name),
    TOP_UP_BY_EXTERNAL_CARD(TopUpCardActivity::class.java.name),
    ADD_FUNDS(AddFundsActivity::class.java.name),
    REMOVE_FUNDS(RemoveFundsActivity::class.java.name),
    Y2Y_TRANSFER(YapToYapDashboardActivity::class.java.name),
    UNFREEZE_CARD(BeneficiaryFundTransferActivity::class.java.name),
    CHANGE_PIN(ChangeCardPinActivity::class.java.name),
    FORGOT_PIN(ForgotCardPinActivity::class.java.name),
    NONE("NONE")
}