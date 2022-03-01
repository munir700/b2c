package com.yap.yappakistan.ui.auth.forgotpasscode.newpasscodesuccess

import androidx.fragment.app.viewModels
import com.yap.core.base.BaseNavFragment
import com.yap.yappakistan.BR
import com.yap.yappakistan.R
import com.yap.yappakistan.databinding.FragmentNewPasscodeSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasscodeSuccessFragment :
    BaseNavFragment<FragmentNewPasscodeSuccessBinding, INewPasscodeSuccess.State, INewPasscodeSuccess.ViewModel>(
        R.layout.fragment_new_passcode_success
    ), INewPasscodeSuccess.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override val viewModel: INewPasscodeSuccess.ViewModel by viewModels<NewPasscodeSuccessVM>()

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnDone -> requireActivity().onBackPressed()
        }
    }

}