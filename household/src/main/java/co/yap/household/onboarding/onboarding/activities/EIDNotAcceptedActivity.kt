package co.yap.household.onboarding.onboarding.activities

import android.os.Bundle
import co.yap.household.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity

class EIDNotAcceptedActivity : DefaultActivity(), IFragmentHolder {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eidnot_accepted)
    }
}
