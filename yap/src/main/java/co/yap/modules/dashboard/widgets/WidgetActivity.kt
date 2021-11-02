package co.yap.modules.dashboard.widgets

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.activity_widget.*

class WidgetActivity : BaseBindingActivity<IWidget.ViewModel>(), IWidget.View {


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_widget

    override val viewModel: WidgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intRecyclersView()
    }

    private fun intRecyclersView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.apply {
            val mAdapter = WidgetAdapter(mutableListOf())
            adapter = mAdapter
            viewModel.widgetAdapter?.set(mAdapter)
            viewModel.getWidgetList()
            recyclerView.setHasFixedSize(true)
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }
}