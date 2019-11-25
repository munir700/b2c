package co.yap.app.modules.startup.fragments

import android.animation.Animator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import co.yap.BR
import co.yap.app.R
import co.yap.app.modules.startup.adapters.WelcomePagerAdapter
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.viewmodels.WelcomeViewModel
import co.yap.yapcore.BaseBindingFragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_onboarding_welcome.*


open class WelcomeFragment : BaseBindingFragment<IWelcome.ViewModel>(), IWelcome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_onboarding_welcome

    override val viewModel: IWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)
    lateinit var item: View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.accountType = getAccountType()
        welcome_pager.offscreenPageLimit = 1

        val welcomePagerAdapter = WelcomePagerAdapter(
            context = requireContext(),
            contents = viewModel.getPages(),
            layout = R.layout.content_onboarding_welcome
        )

        viewModel.onGetStartedPressEvent.observe(this, getStartedButtonObserver)
        //
//
//        if (welcomePagerAdapter.viewsContainer[position] is View) {
//            val item = welcomePagerAdapter.viewsContainer[position] as View
//            val tvTitle = item.findViewById<TextView>(R.id.tvTitle)
//            val tvDescription = item.findViewById<TextView>(R.id.tvDescription)
//            val ivPoster = item.findViewById<ImageView>(R.id.ivPoster)
//            hideAllAnimatingViews(tvTitle,tvDescription,ivPoster)
//            slideInTitle(
//                tvTitle,
//                tvDescription,
//                ivPoster
//            )
//
//        }

        welcome_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0) {
                    hideAllAnimatingViews(
                        welcomePagerAdapter.tvTitle!!,
                        welcomePagerAdapter.tvDescription!!,
                        welcomePagerAdapter.ivPoster!!
                    )
                    hideAllAnimatingViews(welcomePagerAdapter)

                }
                if (::item.isInitialized) {
                    hideAllAnimatingViews(
                        item.findViewById<TextView>(R.id.tvTitle),
                        item.findViewById<TextView>(R.id.tvDescription),
                        item.findViewById<ImageView>(R.id.ivPoster)
                    )

                }

                if (welcomePagerAdapter.viewsContainer[position] is View) {
                    item = welcomePagerAdapter.viewsContainer[position] as View
                    val tvTitle = item.findViewById<TextView>(R.id.tvTitle)
                    val tvDescription = item.findViewById<TextView>(R.id.tvDescription)
                    val ivPoster = item.findViewById<ImageView>(R.id.ivPoster)
                    ivPoster.visibility = INVISIBLE
                    welcomePagerAdapter.ivPoster!!.visibility = INVISIBLE

                    slideInTitle(
                        tvTitle,
                        tvDescription,
                        ivPoster
                    )
                }

            }

            override fun onPageSelected(position: Int) {

                if (position == 0) {
                    hideAllAnimatingViews(
                        welcomePagerAdapter.tvTitle!!,
                        welcomePagerAdapter.tvDescription!!,
                        welcomePagerAdapter.ivPoster!!
                    )
                    hideAllAnimatingViews(welcomePagerAdapter)

                }
                if (::item.isInitialized) {
                    hideAllAnimatingViews(
                        item.findViewById<TextView>(R.id.tvTitle),
                        item.findViewById<TextView>(R.id.tvDescription),
                        item.findViewById<ImageView>(R.id.ivPoster)
                    )

                }


//                if (welcomePagerAdapter.viewsContainer[position] is View) {
//                    val item = welcomePagerAdapter.viewsContainer[position] as View
//                    val tvTitle = item.findViewById<TextView>(R.id.tvTitle)
//                    val tvDescription = item.findViewById<TextView>(R.id.tvDescription)
//                    val ivPoster = item.findViewById<ImageView>(R.id.ivPoster)
//                    hideAllAnimatingViews(tvTitle,tvDescription,ivPoster)
//                    slideInTitle(
//                        tvTitle,
//                        tvDescription,
//                        ivPoster
//                    )
//
//                }

            }

        })

        welcome_pager?.adapter = welcomePagerAdapter
        view?.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)?.setViewPager(welcome_pager)

        welcome_pager!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(
                v: View?,
                event: MotionEvent?
            ): Boolean {
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (event!!.getX() < 490) {
                            // tapped on left of center
                            if (welcome_pager.currentItem > 0 && welcome_pager.currentItem <= 2) {
                                //hideAllAnimatingViews(welcomePagerAdapter!!)
                                welcome_pager.setCurrentItem(welcome_pager.currentItem - 1)
//                                welcome_pager.performClick()

                                return true
                            }

                        }
                        if (event!!.getX() > 490) {
                            //tapped on right of center
                            if (welcome_pager.currentItem < 2) {
                                //hideAllAnimatingViews(welcomePagerAdapter!!)
                                welcome_pager.setCurrentItem(welcome_pager.currentItem + 1)


//                                welcome_pager.performClick()
                                return true

                            }
                        }
                    }

                }

                return false  // setting up false is necessary to consider swipe

            }
        })

    }

    private fun hideAllAnimatingViews(
        tvTitle: TextView,
        tvDescription: TextView,
        ivPoster: ImageView
    ) {
        tvTitle!!.visibility = INVISIBLE
        tvDescription!!.visibility = INVISIBLE
        ivPoster!!.visibility = INVISIBLE
    }

    private fun hideAllAnimatingViews(welcomePagerAdapter: WelcomePagerAdapter) {
        welcomePagerAdapter.tvTitle!!.visibility = INVISIBLE
        welcomePagerAdapter.tvDescription!!.visibility = INVISIBLE
        welcomePagerAdapter.ivPoster!!.visibility = INVISIBLE
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


    fun slideInTitle(viewFirst: View, viewSecond: View, viewThird: View) {
        viewFirst!!.visibility = VISIBLE
        viewThird!!.visibility = INVISIBLE


        YoYo.with(Techniques.SlideInRight)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    viewThird!!.visibility = INVISIBLE
                    slideInDsscription(viewSecond, viewThird)
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
//                    slideInDsscription(viewSecond, viewThird)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(300)
            .repeat(0)
            .playOn(viewFirst)

    }

    fun slideInDsscription(viewSecond: View, viewThird: View) {
        viewSecond!!.visibility = VISIBLE
        viewThird!!.visibility = INVISIBLE

        YoYo.with(Techniques.SlideInRight)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
//                    slideInImage(viewThird)
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    slideInImage(viewThird)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(200)
            .repeat(0)
            .playOn(viewSecond)

    }

    //
    fun slideInImage(viewThird: View) {
        viewThird!!.visibility = VISIBLE

        YoYo.with(Techniques.SlideInRight)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
//                    slideInImage(viewThird)
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {

                 }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(200)
            .repeat(0)
            .playOn(viewThird)

    }

//    fun slideInImage(viewThird: View) {
//        viewThird!!.visibility = VISIBLE
//        YoYo.with(Techniques.SlideInRight)
//            .duration(100)
//            .repeat(0)
//            .playOn(viewThird)
//
//    }
}