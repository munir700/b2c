package co.yap.modules.store.models

import androidx.annotation.Keep

@Keep
data class YapStoreData(
    var id: Int,
    var name: String,
    var desc: String,
    var image: Int,
    var storeIcon: Int
)