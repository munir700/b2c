package co.yap.yapcore

import androidx.annotation.NonNull
import androidx.annotation.Nullable

interface OnFilter<T : Any> {
    fun onFilterApply(@Nullable filter: CharSequence?, @NonNull model: T): Boolean?

    fun onFilterResult(filteredList: MutableList<T>){}
}