package co.yap.config

import android.content.Context
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.featureflag.FeatureDtos
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.coroutines.coroutineScope

class FeatureFlagCall(context: Context) {
    var shardPrefs = SharedPreferenceManager.getInstance(context)
    suspend fun getFeatureFlag(email: String?, customerId: String?) {
        val customersRepository: CustomersApi = CustomersRepository
        coroutineScope {
            when (val response = customersRepository.getFeatureFlag(
                customerId ?: "",
                email ?: ""
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