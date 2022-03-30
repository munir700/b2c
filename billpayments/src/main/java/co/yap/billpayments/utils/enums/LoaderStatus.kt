package co.yap.billpayments.utils.enums

sealed class LoaderStatus {
    object SuccessState : LoaderStatus()
    object ErrorState : LoaderStatus()
    object LoadingState : LoaderStatus()
}