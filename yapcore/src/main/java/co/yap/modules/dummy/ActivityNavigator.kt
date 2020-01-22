package co.yap.modules.dummy

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import co.yap.yapcore.BaseActivity

interface ActivityNavigator {
    fun startEIDNotAcceptedActivity(activity: FragmentActivity)
    fun startVerifyPassCodePresenterActivity(activity: FragmentActivity,completionHandler: ((resultCode: Int, data: Intent?) -> Unit)?)
}