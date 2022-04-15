package co.yap.networking.leanteach.responsedtos.accountlistmodel

import android.os.Parcelable
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountListMainModel(
    @SerializedName("status") var status: String,
    @SerializedName("bank") var bank: BankListMainModel?,
    @SerializedName("leanCustomerAccounts") var leanCustomerAccounts: ArrayList<LeanCustomerAccounts>
) : ApiResponse(), Parcelable