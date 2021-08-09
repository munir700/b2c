package co.yap.modules.kyc.fragments

import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IEditCardName
import co.yap.modules.kyc.viewmodels.EditCardNameViewModel

class EditCardNameFragment : KYCChildFragment<IEditCardName.ViewModel>(), IEditCardName.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_edit_card_name
    override val viewModel: EditCardNameViewModel by viewModels()
    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.onBackPressed()
            }
        }
    }
}