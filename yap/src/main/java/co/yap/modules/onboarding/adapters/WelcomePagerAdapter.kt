package co.yap.modules.onboarding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter
import co.yap.R
import co.yap.databinding.ContentOnboardingWelcomeBinding
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.yapcore.interfaces.IBindable

class WelcomePagerAdapter(
    private val context: Context,
    private val contents: ArrayList<WelcomeContent>,
    private val layout: Int
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val content = contents[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewBinding = DataBindingUtil.inflate<ContentOnboardingWelcomeBinding>(inflater, layout, container, false)
        viewBinding.setVariable((content as IBindable).bindingVariable, content)
        container.addView(viewBinding.root)
        return viewBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return contents[position].title
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === (`object` as View)

    override fun getCount(): Int = contents.size
}