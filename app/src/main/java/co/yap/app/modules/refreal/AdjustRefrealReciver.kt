package co.yap.app.modules.refreal

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.yap.app.main.MainActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.yapcore.adjust.ReferralInfo
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.SessionManager
import com.adjust.sdk.Adjust

class AdjustReferrerReceiver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("AdjustReferrerReceiver", "AdjustReferrerReceiver")
//        toast("AdjustReferrerReceiver", Toast.LENGTH_LONG)
        intent.data?.let { uri ->
            Adjust.appWillOpenUrl(uri, this)
            val customerId = uri.getQueryParameter(Constants.REFERRAL_ID)
            customerId?.let { cusId ->
                uri.getQueryParameter(Constants.REFERRAL_TIME)?.let { time ->
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
                if (isYapDashboardRunning(this)) {
                    val i = Intent(this, YapDashboardActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    finish()
                } else {
                    openLogin()
                }
            } ?: openLogin()
//            run {
////                launchActivityForResult<MainActivity>(init = {
////                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                }, completionHandler = { resultCode, intent ->
////                    if (resultCode == Activity.RESULT_OK) {
////                        SessionManager.deepLinkFlowId.value = id
////                    }
////                    finish()
////                })

            // finish()
//            }
        } ?: run {
            if (isRunning(this))
                finish()
            else
                startLauncherActivity()
        }
    }

    private fun openLogin() {
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    private fun startLauncherActivity() {
        startActivity(packageManager.getLaunchIntentForPackage(packageName))
        finish()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_MAIN) {
//            if (resultCode == Activity.RESULT_OK) {
//                SessionManager.deepLinkFlowId.value = intent.data?.getQueryParameter("flow_id")
//                finish()
//            }
//        }
//    }
}
