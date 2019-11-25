package co.yap.app.modules.startup.adapters

import android.animation.Animator
import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import co.yap.databinding.ContentOnboardingWelcomeBinding
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.yapcore.interfaces.IBindable
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class WelcomePagerAdapter(
    private val context: Context,
    private val contents: ArrayList<WelcomeContent>,
    private val layout: Int
) : PagerAdapter() {
    val viewsContainer: SparseArray<View> = SparseArray()

    var tvTitle: TextView? = null
    var tvDescription: TextView? = null
    var ivPoster: ImageView? = null
    var containerView: View? = null
    var check: Boolean = true

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val content = contents[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewBinding = DataBindingUtil.inflate<ContentOnboardingWelcomeBinding>(
            inflater,
            layout,
            container,
            false
        )
        viewBinding.setVariable((content as IBindable).bindingVariable, content)

        Log.i("positionzzzvv", position.toString())

        tvTitle = viewBinding.tvTitle
        tvDescription = viewBinding.tvDescription
        ivPoster = viewBinding.ivPoster

        tvDescription!!.visibility = GONE
        tvTitle!!.visibility = GONE
        ivPoster!!.visibility = GONE

//        viewBinding.root.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//
//                if (position == 0 ||position == 2) {
//                    tvDescription!!.visibility = VISIBLE
//                    tvTitle!!.visibility = VISIBLE
//                    ivPoster!!.visibility = VISIBLE
//                }
//            return false
//            }
//        })
//  viewBinding.root.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//
//                if (position == 1) {
//                    tvDescription!!.visibility = VISIBLE
//                    tvTitle!!.visibility = VISIBLE
//                    ivPoster!!.visibility = VISIBLE
//                }
//            }
//        })

        container.addView(viewBinding.root)
//        tvTitle = viewBinding.tvTitle
//        tvDescription = viewBinding.tvDescription
//        ivPoster = viewBinding.ivPoster
//        slideInTitle()

        containerView = viewBinding.root

        viewsContainer.put(position, viewBinding.root)
        return viewBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        slideOutTitle()
        container.performClick()
        Log.i("positionzzz", position.toString())
//        container.removeView(`object` as View)

        viewsContainer.remove(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        slideInTitle()
        return contents[position].title
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === (`object` as View)

    override fun getCount(): Int = contents.size


    open fun slideInTitle() {
//        check = false
        tvDescription!!.visibility = VISIBLE
        YoYo.with(Techniques.SlideInRight)
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
            .duration(400)
            .repeat(0)
            .playOn(tvDescription!!)

    }

    open fun slideOutTitle() {
//        tvDescription!!.visibility = VISIBLE

        YoYo.with(Techniques.SlideOutRight)
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
            .duration(300)
            .repeat(0)
            .playOn(tvDescription!!)

    }
}