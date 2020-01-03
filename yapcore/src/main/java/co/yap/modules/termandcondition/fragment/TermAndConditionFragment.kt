package co.yap.modules.termandcondition.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.termandcondition.interfaces.ITermAndCondition
import co.yap.modules.termandcondition.viewmodel.TermAndConditionViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R

class TermAndConditionFragment : BaseBindingFragment<ITermAndCondition.ViewModel>(),
    ITermAndCondition.View {

    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_term_and_condition

    override val viewModel: ITermAndCondition.ViewModel
        get() = ViewModelProviders.of(this).get(TermAndConditionViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
