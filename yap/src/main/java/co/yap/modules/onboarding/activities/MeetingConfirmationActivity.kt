package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.yap.R

class MeetingConfirmationActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MeetingConfirmationActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_confirmation)
    }

}