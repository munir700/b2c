package co.yap.modules.dashboard.store.cardplans.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentViewerCardPlansBinding
import co.yap.modules.dashboard.store.cardplans.CardsFragmentAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardViewer
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlanViewerViewModel
import kotlinx.android.synthetic.main.fragment_viewer_card_plans.*

class CardPlanViewerFragment : CardPlansBaseFragment<ICardViewer.ViewModel>(), ICardViewer.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_viewer_card_plans

    override val viewModel: CardPlanViewerViewModel
        get() = ViewModelProviders.of(this).get(CardPlanViewerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdaptor()
    }

    private fun setupAdaptor() {
        val adaptor = CardsFragmentAdapter(this)
        getBindings().cardViewPager.adapter = adaptor
    }

    override fun initArguments() {
        arguments?.let { bundle ->
            val fragmentId = bundle.getString(viewModel.parentViewModel?.cardTag)
            getBindings().cardViewPager.setCurrentItem(viewModel.getFragmentToDisplay(fragmentId),false)
        }
    }
    override fun getBindings(): FragmentViewerCardPlansBinding =
        viewDataBinding as FragmentViewerCardPlansBinding
}