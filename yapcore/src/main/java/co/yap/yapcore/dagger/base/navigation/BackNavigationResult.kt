package co.yap.yapcore.dagger.base.navigation

import android.os.Bundle

data class BackNavigationResult(val requestCode: Int, val resultCode: Int, val data: Bundle? = null)
