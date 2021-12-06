package co.yap.modules.location.kyc_additional_info.tax_info

interface ITaxItemOnClickListenerInterface {
    fun onRuleValidationComplete(position : Int = -1, isRuleValid : Boolean = false)
}