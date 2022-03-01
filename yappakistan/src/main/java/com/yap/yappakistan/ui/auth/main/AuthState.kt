package com.yap.yappakistan.ui.auth.main

import com.yap.core.base.BaseState
import javax.inject.Inject

class AuthState @Inject constructor() : BaseState(), IAuth.State {
    override var isAccountBlocked: Boolean = false
}