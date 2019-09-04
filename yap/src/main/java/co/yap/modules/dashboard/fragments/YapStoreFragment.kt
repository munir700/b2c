package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.interfaces.IYapStore
import co.yap.modules.dashboard.viewmodels.YapStoreViewModel
import co.yap.yapcore.BaseBindingFragment
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip
import kotlinx.android.synthetic.main.fragment_yap_store.*
import kotlinx.android.synthetic.main.view_graph.view.*

class YapStoreFragment : BaseBindingFragment<IYapStore.ViewModel>(), IYapStore.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_store

    override val viewModel: IYapStore.ViewModel
        get() = ViewModelProviders.of(this).get(YapStoreViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            addTooltip(tv)
        })


        // addTooltip(button)
    }

    var tooltip: Tooltip? = null

    private fun addTooltip(view: View) {
        // val tooltip = transactionsView.findViewById<TooltipView>(R.id.tooltip)
        view.let {
            tooltip?.dismiss()
            tooltip = Tooltip.Builder(requireContext())
                .anchor(it, 0, 0, false)
                // .anchor(Int, Int)
                .text("Hello from dynamic")
                // .styleId(Int)
                .maxWidth(400)
                .arrow(true)
                .floatingAnimation(Tooltip.Animation.DEFAULT)
                // .closePolicy(ClosePolicy.TOUCH_NONE)
                // .showDuration(5000)
                // .fadeDuration(1000)
                .overlay(true)
                .create()
            tooltip?.show(it, Tooltip.Gravity.TOP, true)
        }

    }
}