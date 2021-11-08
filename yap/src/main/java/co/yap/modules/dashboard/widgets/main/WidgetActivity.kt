package co.yap.modules.dashboard.widgets.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.home.models.WidgetItemList
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class WidgetActivity : BaseBindingActivity<IWidget.ViewModel>(), INavigator,
    IFragmentHolder, IWidget.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_widget

    override val viewModel: WidgetViewModel by viewModels()

    companion object {
        const val EDIT_WIDGET = "editWidget"
        fun newIntent(
            context: Context,
            widgetList: WidgetItemList
        ): Intent {
            val intent = Intent(context, WidgetActivity::class.java)
            intent.putExtra(EDIT_WIDGET, widgetList)
            return intent
        }
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@WidgetActivity, R.id.widget_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDatFromBundle()
    }

    private fun getDatFromBundle() {
        intent?.let {
            if (it.hasExtra(EDIT_WIDGET)) {
                it.getParcelableExtra<WidgetItemList>(EDIT_WIDGET).let { widgetList ->
                    viewModel.widgetDataList.addAll(widgetList.widgetData)
                }
            }
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