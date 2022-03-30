package co.yap.networking.transactions.responsedtos.categorybar

import com.google.gson.annotations.SerializedName

data class MonthData(
	@SerializedName("date") val date: String,
	@SerializedName("categories") var categories: List<Categories>
)