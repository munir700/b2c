package co.yap.modules.dashboard.widgets

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.home.models.WidgetItemList
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.activity_widget.*

class WidgetActivity : BaseBindingActivity<IWidget.ViewModel>(), IWidget.View {


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_widget

    override val viewModel: WidgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDatFromBundle()
        intRecyclersView()
    }

    private fun getDatFromBundle() {
        if (intent?.hasExtra(ExtraKeys.EDIT_WIDGET.name) == true) {
            intent.getParcelableExtra<WidgetItemList>(ExtraKeys.EDIT_WIDGET.name)?.let {
                viewModel.widgetDataList.addAll(it.widgetData)
            }
        }
    }

    private fun intRecyclersView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@WidgetActivity)
//            val mAdapter = WidgetAdapter(mutableListOf(), clickListener)
//            adapter = mAdapter
//            viewModel.widgetAdapter?.set(mAdapter)
//            viewModel.filterWidgetDataList()
//            recyclerView.setHasFixedSize(true)
        }
    }

    private val clickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {

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