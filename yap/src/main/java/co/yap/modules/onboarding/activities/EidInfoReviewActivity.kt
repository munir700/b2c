package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.yap.R

class EidInfoReviewActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, EidInfoReviewActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eid_info_review)
    }
}