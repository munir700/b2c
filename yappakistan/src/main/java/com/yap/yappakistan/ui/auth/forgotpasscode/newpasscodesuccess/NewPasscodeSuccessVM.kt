package com.yap.yappakistan.ui.auth.forgotpasscode.newpasscodesuccess

import com.yap.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewPasscodeSuccessVM @Inject constructor(
    override val viewState: NewPasscodeSuccessState
) : BaseViewModel<INewPasscodeSuccess.State>(), INewPasscodeSuccess.ViewModel {
}