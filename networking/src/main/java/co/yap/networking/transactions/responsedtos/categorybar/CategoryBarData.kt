package co.yap.networking.transactions.responsedtos.categorybar

import com.google.gson.annotations.SerializedName

data class CategoryBarData(
	@SerializedName("monthData") val monthData: List<MonthData>
)