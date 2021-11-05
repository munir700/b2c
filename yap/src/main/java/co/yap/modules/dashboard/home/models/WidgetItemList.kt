package co.yap.modules.dashboard.home.models

import android.os.Parcelable
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WidgetItemList(
    @SerializedName("content")
    var widgetData: List<WidgetData>
): Parcelable