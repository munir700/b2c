package co.yap.modules.dashboard.cards.home.viewmodels

import co.yap.networking.cards.responsedtos.Card

class YapCardItemViewModel(val paymentCard: Card?) {

    // Custom logic if there any and add only observable or mutable data if your really need it.
    // You can also add methods for callbacks from xml
    fun handlePressOnView(id: Int) {}
}