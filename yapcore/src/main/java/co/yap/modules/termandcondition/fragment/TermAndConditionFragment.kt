package co.yap.modules.termandcondition.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.termandcondition.interfaces.ITermAndCondition
import co.yap.modules.termandcondition.viewmodel.TermAndConditionViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.fragment_term_and_condition.*


class TermAndConditionFragment : BaseBindingFragment<ITermAndCondition.ViewModel>(),
    ITermAndCondition.View {

    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_term_and_condition

    override val viewModel: ITermAndCondition.ViewModel
        get() = ViewModelProviders.of(this).get(TermAndConditionViewModel::class.java)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTermAndCondition()
    }

    private fun loadTermAndCondition() {
        termConditionWebView.settings.javaScriptEnabled = true
        termConditionWebView.webViewClient = WebViewClient()
        termConditionWebView.loadUrl("https://yap.co/")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
