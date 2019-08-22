package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.interfaces.IDashboard
import co.yap.modules.dashboard.viewmodels.DashBoardViewModel
import co.yap.yapcore.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashBoardHomeFragment : BaseFragment<IDashboard.ViewModel>(), IDashboard.View {

    override val viewModel: IDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(DashBoardViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barSet = linkedMapOf(
            "JAN" to 2.3F,
            "FEB" to 2.3F,
            "MAR" to 2.3F,
            "MAY" to 2.3F,
            "APR" to 2.3F,
            "JUN" to 2.3F,
            "JUL" to 2.3F,
            "AUG" to 2.3F,
            "SEP" to 2.3F,
            "OCT" to 6F,
            "NOV" to 2.3F,
            "DEC" to 3F
//                "JAN" to 4F,
//            "FEB" to 7F,
//            "MAR" to 2F,
//            "MAY" to 2.3F,
//            "APR" to 5F,
//            "JUN" to 4F,
//            "JUL" to 2F,
//            "AUG" to 4F,
//            "SEP" to 5F,
//            "OCT" to 6F,
//            "NOV" to 4F,
//            "DEC" to 3F
        )

        barChart.animation.duration = 1000
        barChart.animate(barSet)

    }
}