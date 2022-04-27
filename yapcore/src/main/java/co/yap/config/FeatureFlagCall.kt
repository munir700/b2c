package co.yap.config

import android.content.Context
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.featureflag.FeatureDtos
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.coroutineScope

class FeatureFlagCall(context: Context) {
    var shardPrefs = SharedPreferenceManager.getInstance(context)
    suspend fun getFeatureFlag() {
        val customersRepository: CustomersApi = CustomersRepository
        coroutineScope {
            when (val response = customersRepository.getFeatureFlag(
                SessionManager.user?.currentCustomer?.customerId ?: "",
                SessionManager.user?.currentCustomer?.email ?: ""
            )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.featureDtos?.let { saveValuesInPref(it) }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    fun saveValuesInPref(featureDtos: ArrayList<FeatureDtos>?) {
        if (featureDtos.isNullOrEmpty().not()) {
            featureDtos?.forEach { feature ->
                feature.key?.let { shardPrefs.save(it, feature.value) }
            }
        }
    }
}