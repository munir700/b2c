package co.yap.billpayments.paybill.enums

sealed class LoaderStatus {
    object SuccessState : LoaderStatus()
    object ErrorState : LoaderStatus()
    object LoadingState : LoaderStatus()
}