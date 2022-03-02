package com.yap.yappakistan.ui.auth.forgotpasscode.newpasscodesuccess

import androidx.hilt.lifecycle.ViewModelInject
import com.yap.core.base.BaseViewModel
import javax.inject.Inject

class NewPasscodeSuccessVM @ViewModelInject constructor(
    override val viewState: NewPasscodeSuccessState
) : BaseViewModel<INewPasscodeSuccess.State>(), INewPasscodeSuccess.ViewModel {
}