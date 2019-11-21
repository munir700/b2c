package co.yap.app.modules.startup.adapters

import android.animation.Animator
import android.content.Context
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
    var tvTitle: TextView? = null
    var tvDescription: TextView? = null
    var ivPoster: ImageView? = null

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
        container.addView(viewBinding.root)
        tvTitle = viewBinding.tvTitle
        tvDescription = viewBinding.tvDescription
        ivPoster = viewBinding.ivPoster
        slideInTitle()

        return viewBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        slideInTitle()
        container.removeView(`object` as View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        slideInTitle()
        return contents[position].title
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === (`object` as View)

    override fun getCount(): Int = contents.size


    fun slideInTitle() {

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
            .duration(1500)
            .repeat(0)
            .playOn(tvDescription)

    }


}