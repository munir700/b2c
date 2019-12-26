package co.yap.modules.dashboard.store.household.activities

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.cards.addpaymentcard.spare.SpareCardsLandingAdapter
import co.yap.modules.dashboard.store.household.interfaces.IHouseHoldSubscription
import co.yap.modules.dashboard.store.household.onboarding.HouseHoldOnboardingActivity
import co.yap.modules.dashboard.store.household.viewmodels.SubscriptionSelectionViewModel
import co.yap.yapcore.BaseBindingActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_house_hold_subscription_selction.*
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class SubscriptionSelectionActivity :
    BaseBindingActivity<IHouseHoldSubscription.ViewModel>(),
    IHouseHoldSubscription.View, SpareCardsLandingAdapter.OnItemClickedListener {

    lateinit var item: View
    var selectedPosition: Int = 0
    var incrementValue: Boolean = true
    var exitEvent: Boolean = false

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_house_hold_subscription_selction

    override val viewModel: IHouseHoldSubscription.ViewModel
        get() = ViewModelProviders.of(this).get(SubscriptionSelectionViewModel::class.java)

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SubscriptionSelectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      addBenefitRecyclerView()
        initViewPager(llContainer)

    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.btnClose -> {

                    finish() // will check in fsd/story till where is it required to go back
                }
                R.id.llAnnualSubscription -> {
                    viewModel.state.hasSelectedPackage = true

                    llMonthlySubscription.isActivated = false
                    llAnnualSubscription.isActivated = true
                }


                R.id.llMonthlySubscription -> {

                    viewModel.state.hasSelectedPackage = true

                    llMonthlySubscription.isActivated = true
                    llAnnualSubscription.isActivated = false
                }

                R.id.btnGetStarted -> {
                    startActivity(HouseHoldOnboardingActivity.newIntent(this))

                }

                R.id.imgClose -> {
                    finish()
                }

            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)

    }

    private fun addBenefitRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rvSubscriptionPackageBenefits.layoutManager = layoutManager
        rvSubscriptionPackageBenefits.isNestedScrollingEnabled = false
        rvSubscriptionPackageBenefits.adapter =
            SpareCardsLandingAdapter(
                viewModel.loadDummyData(),
                this
            )
    }

    override fun onItemClick(benefitsModel: BenefitsModel) {
//        start benefits screen if required

    }

    fun initViewPager(view: View) {

        val welcomePagerAdapter = SubscriptionPagerAdapter(
            context = this,
            contents = viewModel.getPages(),
            layout = R.layout.content_onboarding_welcome
        )

//        viewModel.onGetStartedPressEvent.observe(this, getStartedButtonObserver)

        welcome_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
//                if (position == 0) {
                if (!exitEvent) {
                    exitEvent = true
                    if (welcomePagerAdapter.viewsContainer[position] is View) {
                        item = welcomePagerAdapter.viewsContainer[position] as View
                        slideInTitle(
                            item.findViewById<TextView>(R.id.tvTitle),
                            item.findViewById<TextView>(R.id.tvDescription),
                            item.findViewById<ImageView>(R.id.ivPoster)
                        )
                    }
                }
                selectedPosition = position

            }

            override fun onPageSelected(position: Int) {
//                if (position != 0) {
                if (exitEvent) {
                    if (welcomePagerAdapter.viewsContainer[position] is View) {
                        item = welcomePagerAdapter.viewsContainer[position] as View

                        slideInTitle(
                            item.findViewById<TextView>(R.id.tvTitle),
                            item.findViewById<TextView>(R.id.tvDescription),
                            item.findViewById<ImageView>(R.id.ivPoster)
                        )
                    }
                }

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


                                if (::item.isInitialized) {
                                    incrementValue = false

                                    return slideOutTitle(
                                        item.findViewById<TextView>(R.id.tvTitle),
                                        item.findViewById<TextView>(R.id.tvDescription),
                                        item.findViewById<ImageView>(R.id.ivPoster)
                                    )
                                }
                                return true

                            }

                        }
                        if (event!!.getX() > 490) {
                            //tapped on right of center
                            if (welcome_pager.currentItem < 2) {

                                if (::item.isInitialized) {
                                    incrementValue = true
                                    return slideOutTitle(
                                        item.findViewById<TextView>(R.id.tvTitle),
                                        item.findViewById<TextView>(R.id.tvDescription),
                                        item.findViewById<ImageView>(R.id.ivPoster)
                                    )

                                }
                                return true


                            }
                        }
                        return true
                    }

                }

                return true  // setting up false is necessary to consider swipe

            }
        })
    }

    fun slideInTitle(viewFirst: View, viewSecond: View, viewThird: View) {
        viewFirst!!.visibility = View.VISIBLE

        var techniques: Techniques
        if (incrementValue) {
            techniques = Techniques.SlideInRight
        } else {
            techniques = Techniques.SlideInLeft

        }

        YoYo.with(techniques)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    slideInDsscription(viewSecond, viewThird)
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(400)
            .repeat(0)
            .playOn(viewFirst)

    }

    fun slideInDsscription(viewSecond: View, viewThird: View) {
        var techniques: Techniques
        if (incrementValue) {
            techniques = Techniques.SlideInRight
        } else {
            techniques = Techniques.SlideInLeft

        }

        viewSecond!!.visibility = View.VISIBLE

        YoYo.with(techniques)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    slideInImage(viewThird)
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
            .playOn(viewSecond)

    }

    fun slideInImage(viewThird: View) {
        var techniques: Techniques
        if (incrementValue) {
            techniques = Techniques.SlideInRight
        } else {
            techniques = Techniques.SlideInLeft

        }
        viewThird!!.visibility = View.VISIBLE

        YoYo.with(techniques)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
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


    fun slideOutTitle(viewFirst: View, viewSecond: View, viewThird: View): Boolean {

        var techniques: Techniques
        if (incrementValue) {
            techniques = Techniques.SlideOutLeft
        } else {
            techniques = Techniques.SlideOutRight

        }
        YoYo.with(techniques)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    slideOutDsscription(viewSecond, viewThird)
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(400)
            .repeat(0)
            .playOn(viewFirst)
        return true
    }

    fun slideOutDsscription(viewSecond: View, viewThird: View) {

        var techniques: Techniques
        if (incrementValue) {
            techniques = Techniques.SlideOutLeft
        } else {
            techniques = Techniques.SlideOutRight

        }
        YoYo.with(techniques)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    slideOutImage(viewThird)
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
            .playOn(viewSecond)

    }

    fun slideOutImage(viewThird: View) {

        var techniques: Techniques
        if (incrementValue) {
            techniques = Techniques.SlideOutLeft
        } else {
            techniques = Techniques.SlideOutRight

        }
        YoYo.with(techniques)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (incrementValue) {
                        welcome_pager.setCurrentItem(welcome_pager.currentItem + 1)
                    } else {
                        welcome_pager.setCurrentItem(welcome_pager.currentItem - 1)
                    }

                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(200)
            .repeat(0)
            .playOn(viewThird)

    }
}