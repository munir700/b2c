package co.yap.modules.dashboard.widgets

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.fragment_transaction_search.*

class WidgetActivity : BaseBindingActivity<IWidget.ViewModel>(), IWidget.View {


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_widget

    override val viewModel: WidgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intRecyclersView()
    }

    private fun intRecyclersView() {

    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }
}