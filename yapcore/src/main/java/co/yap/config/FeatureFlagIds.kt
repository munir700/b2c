package co.yap.config

sealed class FeatureFlagIds {
    class BillPayment(var bill_payments: String = "bill_payments") : FeatureFlagIds()
    class MyTestFeature(var my_test_feature: String = "my_test_feature") : FeatureFlagIds()
    class BillPaymentsScheduler(var bill_payments_scheduler: String = "bill_payments_scheduler") : FeatureFlagIds()
    class Internal(var internal: String = "internal") : FeatureFlagIds()
    class LeanTechPayments(var lean_tech_payments: String = "lean_tech_payments") : FeatureFlagIds()
}