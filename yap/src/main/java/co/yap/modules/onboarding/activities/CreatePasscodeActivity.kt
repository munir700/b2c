package co.yap.modules.onboarding.activities

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.modules.passcode.IPassCode
import co.yap.modules.passcode.PassCodeViewModel
import co.yap.modules.pdf.PDFActivity
import co.yap.modules.webview.WebViewFragment
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.URL_TERMS_CONDITION
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.databinding.FragmentPassCodeBinding
import co.yap.yapcore.helpers.extentions.startFragment

class CreatePasscodeActivity : BaseBindingActivity<FragmentPassCodeBinding, IPassCode.ViewModel>(),
    IPassCode.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_pass_code

    override val viewModel: IPassCode.ViewModel
        get() = ViewModelProvider(this).get(PassCodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setLayoutVisibility(true)
        with(viewModel.state) {
            forgotTextVisibility = false
            title = getString(Strings.screen_create_passcode_display_heading)
            buttonTitle =
                getString(Strings.screen_create_passcode_onboarding_button_create_passcode)
        }

        getBinding().tvTermsAndConditions.apply {
            visibility = View.VISIBLE
            text = getTermsSpannable()
            movementMethod = LinkMovementMethod.getInstance()
        }

        getBinding().dialer.hideFingerprintView()
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tvTermsAndConditions -> {
                    startFragment<WebViewFragment>(
                        fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                            Constants.PAGE_URL to URL_TERMS_CONDITION,
                            Constants.TOOLBAR_TITLE to getString(R.string.screen_confirm_card_pin_display_text_terms_and_conditions)
                        ), showToolBar = false
                    )
                }
                R.id.btnAction -> {
                    if (viewModel.isValidPassCode()) {
                        setIntentResults()
                    }
                }
            }
        })
    }

    private fun setIntentResults() {
        val intent = Intent()
        intent.putExtra("PASSCODE", viewModel.state.passCode)
        setResult(RequestCodes.REQUEST_CODE_CREATE_PASSCODE, intent)
        finish()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    fun getBinding(): FragmentPassCodeBinding {
        return viewDataBinding
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                super.onBackPressed()
            }
        }
    }

    private fun getTermsSpannable(): SpannableStringBuilder {
        val termsLink =
            getString(R.string.screen_confirm_card_pin_display_text_terms_and_conditions)
        val keyFactStatementLink =
            getString(R.string.screen_confirm_card_pin_display_text_key_fact_statement)
        val termsText = getString(
            R.string.screen_confirm_card_pin_terms_condition_note,
            termsLink,
            keyFactStatementLink
        )

        val termsStartPos = termsText.indexOf(termsLink)
        val policyStartPos = termsText.indexOf(keyFactStatementLink)
        val termsSpannableText = SpannableStringBuilder(termsText)

        termsSpannableText.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    startFragment<WebViewFragment>(
                        fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                            Constants.PAGE_URL to URL_TERMS_CONDITION,
                            Constants.TOOLBAR_TITLE to getString(R.string.screen_confirm_card_pin_display_text_terms_and_conditions)
                        ), showToolBar = false
                    )

                }
            },
            termsStartPos, termsStartPos + termsLink.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        termsSpannableText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
            termsStartPos, termsStartPos + termsLink.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        termsSpannableText.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    startActivity(
                        PDFActivity
                            .newIntent(context, Constants.URL_KEY_FACT_STATEMENT, false)
                    )
                }
            },
            policyStartPos,
            policyStartPos + keyFactStatementLink.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        termsSpannableText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
            policyStartPos,
            policyStartPos + keyFactStatementLink.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return termsSpannableText
    }
}
