package co.yap.modules.dummy

import androidx.fragment.app.FragmentActivity
import co.yap.yapcore.BaseActivity

interface ActivityNavigator {
    fun startEIDNotAcceptedActivity(activity: FragmentActivity)
}