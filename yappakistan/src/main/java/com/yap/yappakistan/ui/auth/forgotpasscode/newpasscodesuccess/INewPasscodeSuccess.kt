package com.yap.yappakistan.ui.auth.forgotpasscode.newpasscodesuccess

import com.yap.core.base.interfaces.IBase

interface INewPasscodeSuccess {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State

}