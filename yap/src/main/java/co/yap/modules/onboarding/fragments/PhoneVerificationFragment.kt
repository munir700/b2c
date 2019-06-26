package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.viewmodels.PhoneVerificationViewModel
import co.yap.yapcore.BaseFragment
import co.yap.yapcore.BaseNavFragment


class PhoneVerificationFragment : BaseFragment<IPhoneVerification.ViewModel>(), IPhoneVerification.View {

    override val viewModel: IPhoneVerification.ViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_verification, container, false)
    }

}
