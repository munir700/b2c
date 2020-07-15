package co.yap.app.modules.refreal

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.yap.yapcore.adjust.ReferralInfo
import co.yap.yapcore.helpers.SharedPreferenceManager
import com.adjust.sdk.Adjust

class AdjustReferrerReceiver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.data?.let { uri ->
            Adjust.appWillOpenUrl(uri, this)
            val customerId = uri.getQueryParameter("inviter")
            customerId?.let { cusId ->
                uri.getQueryParameter("time")?.let { time ->
                    val date = time.replace("_", " ")
                    SharedPreferenceManager(this).setReferralInfo(
                        ReferralInfo(
                            cusId,
                            date
                        )
                    )
                    takeDecision()
                } ?: takeDecision()
            } ?: takeDecision()
        } ?: takeDecision()
    }

    private fun isRunning(ctx: Context): Boolean {
        val activityManager =
            ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks: List<ActivityManager.RunningTaskInfo> =
            activityManager.getRunningTasks(Int.MAX_VALUE)
        for (task in tasks) {
            if ("co.yap.app.activities.MainActivity".equals(
                    task.baseActivity.shortClassName,
                    ignoreCase = true
                )
                || "co.yap.modules.dashboard.main.activities.YapDashboardActivity".equals(
                    task.baseActivity.shortClassName,
                    ignoreCase = true
                )
            ) {
                return true
            }
        }
        return false
    }

    private fun takeDecision() {
        if (isRunning(this))
            finish()
        else
            startLauncherActivity()
    }

    private fun startLauncherActivity() {
        startActivity(packageManager.getLaunchIntentForPackage(packageName))
        finish()
    }
}
