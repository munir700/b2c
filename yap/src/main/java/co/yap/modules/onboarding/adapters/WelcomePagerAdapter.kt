package co.yap.modules.onboarding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import co.yap.modules.onboarding.models.WelcomeContent

class WelcomePagerAdapter(private val context: Context, private val contents: ArrayList<WelcomeContent>, private val layout: Int) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(layout, container, false) as ViewGroup
        container.addView(view)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = contents.size
}