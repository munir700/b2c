package co.yap.networking.cards.requestdtos

data class ChangeCardPinRequest(var oldPin: String, var newPin: String, var confirmPin: String) {
}