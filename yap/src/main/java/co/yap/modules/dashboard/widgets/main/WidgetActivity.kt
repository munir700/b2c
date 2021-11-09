package co.yap.modules.dashboard.widgets.main

import android.os.Bundle
import androidx.activity.viewModels
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.home.models.WidgetItemList
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.interfaces.IBaseNavigator

class WidgetActivity : BaseBindingActivity<IWidget.ViewModel>(), INavigator,
    IFragmentHolder, IWidget.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_widget

    override val viewModel: WidgetViewModel by viewModels()

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@WidgetActivity, R.id.widget_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDatFromBundle()
    }

    private fun getDatFromBundle() {
        if (intent?.hasExtra(ExtraKeys.EDIT_WIDGET.name) == true) {
            intent.getParcelableExtra<WidgetItemList>(ExtraKeys.EDIT_WIDGET.name)?.let {
                viewModel.widgetDataList.addAll(it.widgetData)
            }
        }
    }


}