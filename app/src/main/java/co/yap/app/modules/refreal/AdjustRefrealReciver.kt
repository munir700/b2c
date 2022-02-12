package co.yap.app.modules.refreal

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.yap.app.BuildConfig
import co.yap.app.YAPApplication
import co.yap.app.main.MainActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.yapcore.adjust.ReferralInfo
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.firebase.getFCMToken
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.SessionManager
import com.adjust.sdk.Adjust
import kotlinx.coroutines.launch

class AdjustReferrerReceiver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            getFCMToken {
                Adjust.setPushToken(it, applicationContext)
            }
        }
        if (intent.resolveActivity(packageManager) != null && intent.resolveActivity(packageManager).packageName == BuildConfig.APPLICATION_ID) {
            intent.data?.let { uri ->
                Adjust.appWillOpenUrl(uri, this)
                val customerId = uri.getQueryParameter(Constants.REFERRAL_ID)
                customerId?.let { cusId ->
                    uri.getQueryParameter(Constants.REFERRAL_TIME)?.let { time ->
                        SharedPreferenceManager.getInstance(this).setReferralInfo(
                            ReferralInfo(
                                cusId,
                                time
                            )
                        )
                        takeDecision()
                    } ?: takeDecision()
                } ?: takeDecision()
            } ?: takeDecision()
        }
    }

    private fun isRunning(ctx: Context): Boolean {
        val activityManager =
            ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks: List<ActivityManager.RunningTaskInfo> =
            activityManager.getRunningTasks(Int.MAX_VALUE)
        for (task in tasks) {
            if (MainActivity::class.java.canonicalName.equals(
                    task.baseActivity?.shortClassName,
                    ignoreCase = true
                )
                || YapDashboardActivity::class.java.canonicalName.equals(
                    task.baseActivity?.shortClassName,
                    ignoreCase = true
                )
            ) {
                return true
            }
        }
        return false
    }

    private fun isYapDashboardRunning(ctx: Context): Boolean {
        val activityManager =
            ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks: List<ActivityManager.RunningTaskInfo> =
            activityManager.getRunningTasks(Int.MAX_VALUE)
        for (task in tasks) {
            if (YapDashboardActivity::class.java.canonicalName.equals(
                    task.baseActivity?.shortClassName,
                    ignoreCase = true
                )
            ) {
                return true
            }
        }
        return false
    }

    private fun takeDecision() {
        intent.data?.getQueryParameter("flow_id")?.let { id ->
            SessionManager.deepLinkFlowId.value = id
            SessionManager.user?.let {
                launchYapDashboard()
            } ?: openLogin()
        } ?: run {
            intent.getBundleExtra(Constants.EXTRA)?.let {
                SessionManager.deepLinkFlowId.value = it.getString("flow_id")
                SessionManager.user?.let {
                    launchYapDashboard()
                } ?: openLogin()
            } ?: run {
//                if (isRunning(this))
//                   finish()
//                else
                startLauncherActivity()
            }
        }
    }

    private fun launchYapDashboard() {
        if (isYapDashboardRunning(this)) {
            val i = Intent(this, YapDashboardActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        } else {
            openLogin()
        }
    }

    private fun openLogin() {
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    private fun startLauncherActivity() {
        YAPApplication.AUTO_RESTART_APP = false
        val i = packageManager.getLaunchIntentForPackage(packageName)
        i?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        i?.data = intent.data
        i?.putExtras(intent)
        startActivity(i)
        finishAffinity()
    }
}
