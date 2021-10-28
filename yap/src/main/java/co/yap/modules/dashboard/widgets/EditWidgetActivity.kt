package co.yap.modules.dashboard.widgets

import android.os.Bundle
import androidx.activity.viewModels
import co.yap.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.R

class EditWidgetActivity : BaseBindingActivity<IEditWidget.ViewModel>(), IEditWidget.View{
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_edit_widget

    override val viewModel: EditWidgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }
}