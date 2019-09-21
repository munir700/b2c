package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities

import android.animation.AnimatorSet
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.view.View
import androidx.core.animation.addListener
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.AddFundsViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_fund_actions.*
import kotlinx.android.synthetic.main.layout_card_info.*


open class AddFundsActivity : BaseBindingActivity<IFundActions.ViewModel>(),
    IFundActions.View {

    var amount: String? = null
    private val windowSize: Rect = Rect()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_fund_actions

    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProviders.of(this).get(AddFundsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = this.windowManager.defaultDisplay
        display.getRectSize(windowSize)
        clBottomNew.children.forEach { it.alpha = 0f }
        etAmount.filters = arrayOf<InputFilter>(
            DecimalDigitsInputFilter(2)
        )
        setObservers()
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> (if (viewModel.state.buttonTitle != getString(Strings.screen_success_funds_transaction_display_text_button)) {
                    viewModel.state.topUpSuccess =
                        getString(Strings.screen_success_funds_transaction_display_text_top_up).format(
                            viewModel.state.currencyType,
                            viewModel.state.amount
                        )
                    performSuccessOperations()
                    etAmount.visibility = View.GONE
                    viewModel.state.buttonTitle =
                        getString(Strings.screen_success_funds_transaction_display_text_button)
                } else {
                    this.finish()
                })
                R.id.ivCross -> this.finish()
            }


        })
    }

    private fun showErrorSnackBar() {
        CustomSnackbar.showErrorCustomSnackbar(
            context = this,
            layout = clSnackbar,
            message = viewModel.state.errorDescription
        )
    }

    fun performSuccessOperations() {
        YoYo.with(Techniques.FadeOut)
            .duration(300)
            .repeat(0)
            .playOn(ivCross)
        clBottom.children.forEach { it.alpha = 0f }
        clRightData.children.forEach { it.alpha = 0f }
        Handler(Looper.getMainLooper()).postDelayed({ runAnimations() }, 1500)
        runCardAnimation()
    }


    private fun runAnimations() {
        startCheckMarkAnimation()
        AnimationUtils.runSequentially(
            AnimationUtils.runTogether(
                AnimationUtils.jumpInAnimation(tvCardNameSuccess),
                AnimationUtils.jumpInAnimation(tvCardNumberSuccess).apply { startDelay = 100 },
                AnimationUtils.jumpInAnimation(tvTopUp).apply { startDelay = 200 },
                AnimationUtils.jumpInAnimation(tvPrimaryCardBalance).apply { startDelay = 300 },
                AnimationUtils.jumpInAnimation(tvNewSpareCardBalance).apply { startDelay = 400 },
                AnimationUtils.jumpInAnimation(btnAction).apply { startDelay = 600 }
            )
        ).apply {
            addListener(onEnd = {
            })
        }.start()
    }

    private fun startCheckMarkAnimation() {
        YoYo.with(Techniques.BounceIn)
            .duration(1000)
            .repeat(0)
            .playOn(ivSuccessCheckMark)
    }


    private fun runCardAnimation() {
        Handler(Looper.getMainLooper()).postDelayed({
            cardAnimation().apply {
                addListener(onEnd = {
                })
            }.start()
        }, 500)
    }


    private fun cardAnimation(): AnimatorSet {
        val checkBtnEndPosition = (windowSize.width() / 3) - (ivCustomCard.width / 2)
        return AnimationUtils.runSequentially(
            AnimationUtils.slideHorizontal(
                view = ivCustomCard,
                from = ivCustomCard.x,
                to = checkBtnEndPosition.toFloat(),
                duration = 500
            ),
            AnimationUtils.pulse(ivCustomCard)
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

}