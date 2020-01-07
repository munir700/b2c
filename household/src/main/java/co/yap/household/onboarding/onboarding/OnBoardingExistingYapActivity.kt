package co.yap.household.onboarding.onboarding

import android.os.Bundle
import co.yap.household.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity

class OnBoardingExistingYapActivity : DefaultActivity(), IFragmentHolder {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_existing_yap)
    }
}
