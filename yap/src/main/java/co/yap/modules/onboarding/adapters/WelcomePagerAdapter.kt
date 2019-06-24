package co.yap.modules.onboarding.adapters

import android.view.View
import androidx.viewpager.widget.PagerAdapter
import co.yap.modules.onboarding.models.WelcomeContent

class WelcomePagerAdapter(val contents: ArrayList<WelcomeContent>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = contents.size
}