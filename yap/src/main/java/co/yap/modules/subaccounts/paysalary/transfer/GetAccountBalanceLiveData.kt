package co.yap.modules.subaccounts.paysalary.transfer

import co.yap.networking.models.ApiResponse

class GetAccountBalanceLiveData : LiveDataCallAdapter<ApiResponse>() {

    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
        cancelAllJobs()
    }
}