package co.yap.app.activities

import android.os.Bundle
import co.yap.app.R
import co.yap.app.di.BaseActivity
import co.yap.yapcore.IBase
import kotlinx.android.synthetic.main.activity_account_selection.*


class AccountSelectionActivity : BaseActivity<IBase.ViewModel<IBase.State>>() {
    override val viewModel: IBase.ViewModel<IBase.State>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_selection)

        btnBusiness.setOnClickListener {
            showToast("start b2b onboarding Flow")
        }

        btnPersonal.setOnClickListener {
            showToast("start b2c onboarding Flow")
        }

        tvSignIn.setOnClickListener {

            showToast("start sign in screen here")
        }
    }
}
