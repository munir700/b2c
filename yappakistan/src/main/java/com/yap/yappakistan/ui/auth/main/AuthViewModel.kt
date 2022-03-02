package com.yap.yappakistan.ui.auth.main

import androidx.hilt.lifecycle.ViewModelInject
import com.yap.core.base.BaseViewModel
import javax.inject.Inject

class AuthViewModel @ViewModelInject constructor(override val viewState: AuthState) :
    BaseViewModel<IAuth.State>(), IAuth.ViewModel {
    override var mobileNo: String = ""
    override var countryCode: String = ""
    override var passcode: String = ""
}