package co.yap.yapcore.helpers

import android.content.Context
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.notification.HomeNotification
import co.yap.networking.notification.NotificationAction
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.enums.PartnerBankStatus

object NotificationHelper {
    fun getNotifications(
        accountInfo: AccountInfo?,
        paymentCard: Card?,
        context: Context
    ): ArrayList<HomeNotification> {
        val list = ArrayList<HomeNotification>()
        if (accountInfo?.otpBlocked == true) {
            list.add(
                HomeNotification(
                    id = "1",
                    description = Translator.getString(
                        context,
                        Strings.screen_home_help_and_support_desc
                    ),
                    action = NotificationAction.HELP_AND_SUPPORT
                )
            )
        }
        if ((accountInfo?.notificationStatuses == AccountStatus.ON_BOARDED.name ||
                    accountInfo?.notificationStatuses == AccountStatus.CAPTURED_EID.name) &&
            accountInfo.partnerBankStatus != PartnerBankStatus.ACTIVATED.status
        ) {
            list.add(
                HomeNotification(
                    id = "2",
                    title = Translator.getString(
                        context,
                        Strings.screen_home_complete_verification_title
                    ),
                    description = Translator.getString(
                        context,
                        Strings.screen_home_complete_verification_desc
                    ),
                    action = NotificationAction.COMPLETE_VERIFICATION
                )
            )
        }

        if (shouldShowSetPin(paymentCard) && accountInfo?.partnerBankStatus == PartnerBankStatus.ACTIVATED.status
        ) {
            list.add(
                HomeNotification(
                    id = "3",
                    title = Translator.getString(context, Strings.screen_home_set_pin_title),
                    description = Translator.getString(context, Strings.screen_home_set_pin_desc),
                    action = NotificationAction.SET_PIN
                )
            )
        }
        if ((accountInfo?.notificationStatuses == AccountStatus.EID_EXPIRED.name ||
                    accountInfo?.notificationStatuses == AccountStatus.EID_RESCAN_REQ.name) &&
            accountInfo.partnerBankStatus == PartnerBankStatus.ACTIVATED.status
        ) {
            list.add(
                HomeNotification(
                    id = "4",
                    title = Translator.getString(context, Strings.screen_home_renewed_id_title),
                    description = Translator.getString(
                        context,
                        Strings.screen_home_renewed_id_desc
                    ),
                    action = NotificationAction.UPDATE_EMIRATES_ID
                )
            )
        }

        return list
    }

    private fun shouldShowSetPin(paymentCard: Card?): Boolean {
        return (paymentCard?.deliveryStatus == CardDeliveryStatus.SHIPPED.name && !paymentCard.pinCreated)
    }

}