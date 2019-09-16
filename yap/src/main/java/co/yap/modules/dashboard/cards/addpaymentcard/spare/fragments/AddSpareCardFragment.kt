package co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels.AddSpareCardViewModel
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.layout_add_spare_virtaul_card_confirm_purchase.*
import kotlinx.android.synthetic.main.layout_add_spare_virtual_card_success.*

class AddSpareCardFragment : AddPaymentChildFragment<IAddSpareCard.ViewModel>(),
    IAddSpareCard.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_spare_card

    override val viewModel: IAddSpareCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddSpareCardViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cardType =
            arguments?.let { AddSpareCardFragmentArgs.fromBundle(it).cardType } as String

        viewModel.state.cardType = viewModel.cardType
        fadeOutView(tvTransactionComplete)
        fadeOutView(flTransactionComplete)
        fadeOutView(btnDoneAddingSpareVirtualCard)


        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.clContainer -> {
                    activity!!.recreate()


                }
                R.id.btnDoneAddingSpareVirtualCard -> {

                }
                R.id.btnConfirmVirtualCardPurchase -> {
// hide tha success layout or kill this fragment and go back


                    llConfirmVirtualCardPurchase.visibility = View.GONE
                    layoutVirtualCardOnSuccess.visibility = View.VISIBLE
//                    tvTransactionComplete.visibility = View.VISIBLE
                    YoYo.with(Techniques.FadeOut)
                        .withListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator?) {
                            }

                            override fun onAnimationRepeat(animation: Animator?) {
                            }

                            override fun onAnimationEnd(animation: Animator?) {

                                slideInTitle()
                            }

                            override fun onAnimationCancel(animation: Animator?) {
                            }
                        })
                        .duration(50)
                        .repeat(0)
                        .playOn(llConfirmVirtualCardPurchase)

                    // now remove old include layout_add_spare_virtualcard_confirm_purchase : hide llConfirmVirtualCardPurchase
                    //then animate IN new layout layout_add_spare_virtual_card_success  : show layoutVirtualCardOnSuccess


                }


            }
        })

    }

    fun slideInTitle() {
        YoYo.with(Techniques.SlideInUp)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    FadeInSuccessImage()
//                    slideInDownTitle()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(800)
            .repeat(0)
            .playOn(tvTransactionComplete)

    }

    fun slideInDownTitle() {
        YoYo.with(Techniques.SlideInDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    FadeInSuccessImage()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(800)
            .repeat(0)
            .playOn(tvTransactionComplete)
    }


    fun FadeInSuccessImage() {
//        flTransactionComplete.visibility=View.VISIBLE

        YoYo.with(Techniques.ZoomIn)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
//                    slideInDownImage()
                    slideInBtn()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(800)
            .repeat(0)
            .playOn(flTransactionComplete)
    }


    fun slideInDownImage() {
        YoYo.with(Techniques.SlideInDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    slideInBtn()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(1000)
            .repeat(0)
            .playOn(flTransactionComplete)
    }
//


    fun slideInBtn() {
//        btnDoneAddingSpareVirtualCard.visibility=View.VISIBLE

        YoYo.with(Techniques.SlideInUp)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
//                    slideInDownBtn()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(800)
            .repeat(0)
            .playOn(btnDoneAddingSpareVirtualCard)
    }

    fun slideInDownBtn() {
        YoYo.with(Techniques.SlideInDown)
            .withListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
//                    FadeInSuccessImage()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            .duration(1000)
            .repeat(0)
            .playOn(btnDoneAddingSpareVirtualCard)
    }

    fun fadeOutView(view: View) {
        YoYo.with(Techniques.FadeOut)
            .duration(50)
            .playOn(view)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

}