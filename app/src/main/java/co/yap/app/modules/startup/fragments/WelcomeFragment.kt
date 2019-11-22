package co.yap.app.modules.startup.fragments

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.app.R
import co.yap.app.modules.startup.adapters.WelcomePagerAdapter
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.viewmodels.WelcomeViewModel
import co.yap.yapcore.BaseBindingFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_onboarding_welcome.*


class WelcomeFragment : BaseBindingFragment<IWelcome.ViewModel>(), IWelcome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_onboarding_welcome

    override val viewModel: IWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.accountType = getAccountType()
        var welcomePagerAdapter: WelcomePagerAdapter

        welcomePagerAdapter = WelcomePagerAdapter(
            context = requireContext(),
            contents = viewModel.getPages(),
            layout = R.layout.content_onboarding_welcome
        )
        welcome_pager?.adapter = welcomePagerAdapter

        view?.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)?.setViewPager(welcome_pager)
        viewModel.onGetStartedPressEvent.observe(this, getStartedButtonObserver)

        var tocuchEnable: Boolean = true

        welcome_pager!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(
                v: View?,
                event: MotionEvent?
            ): Boolean {
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {

                        Log.i("eventxyz", "2 == " + event!!.getX().toString())

                        if (event!!.getX() < 490) {
                            //tapped on left of center
                            if (welcome_pager.currentItem > 0) {
                                welcome_pager.setCurrentItem(welcome_pager.currentItem - 1)
                            }
                            return true
                        }
                        if (event!!.getX() > 490) {
                            //tapped on right of center
                            if (welcome_pager.currentItem < 2) {
                                welcome_pager.setCurrentItem(welcome_pager.currentItem + 1)
                                return true

                            }
                        }
                    }
//
//                    MotionEvent.ACTION_DOWN -> {
//                        if (welcome_pager.currentItem<2){
//                            welcome_pager.setCurrentItem(welcome_pager.currentItem + 1)
//                            return true
//
//                        }
//}
                }

                return false  // setting up false is necessary to consider swipe

            }
        })
    }


    override fun onDestroyView() {
        viewModel.onGetStartedPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val getStartedButtonObserver = Observer<Boolean> {
        findNavController().navigate(R.id.action_welcomeFragment_to_onboardingActivity, arguments)
    }

    private fun getAccountType(): AccountType =
        arguments?.getSerializable(getString(R.string.arg_account_type)) as AccountType

}