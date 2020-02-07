package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YAPForYouViewModel
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_yap_for_you.*

class YAPForYouFragment : YapForYouBaseFragment<IYAPForYou.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_for_you
    lateinit var adapter: YAPForYouAdapter


    override val viewModel: IYAPForYou.ViewModel
        get() = ViewModelProviders.of(this).get(YAPForYouViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setupRecycleView()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

//                R.id.tvFollowOnInstagram -> {
//                    Utils.openInstagram(requireContext())
//                }
            }
        })
    }




    private fun setupRecycleView() {
        adapter = YAPForYouAdapter(requireContext(), viewModel.getAchievements())
        rvYapForYou.adapter = adapter
        adapter.allowFullItemClickListener = true
        adapter.setItemListener(listener)
    }

    private fun setObservers() {
//        viewModel.clickEvent.observe(this, observer)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {

            if (data is Achievements) {
                viewModel.selectedAchievement = data

                when (data.id) {
                    1 -> {
                         showToast(data.id.toString())

                    }
                    2 -> {
                        showToast(data.id.toString())

                    }
                    3 -> {
                        showToast(data.id.toString())
                    }
                    4 -> {
                        showToast(data.id.toString())

                    }
                    5 -> {
                        showToast(data.id.toString())

                    }
                    6 -> {
                        showToast(data.id.toString())

                    }
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}