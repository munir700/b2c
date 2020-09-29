package co.yap.modules.dashboard.store.young.confirmrelationship

import android.os.Bundle
import android.widget.CompoundButton
import androidx.core.view.isNotEmpty
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungConfirmRelationshipBinding
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import kotlinx.android.synthetic.main.fragment_young_confirm_relationship.*

class YoungConfirmRelationshipFragment :
    BaseNavViewModelFragment<FragmentYoungConfirmRelationshipBinding, IYoungConfirmRelationship.State, YoungConfirmRelationshipVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_confirm_relationship
    override fun toolBarVisibility() = true
    override fun getToolBarTitle() =
        getString(Strings.screen_young_confirm_relationship_toolbar_text)

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        scTermscondition.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            viewModel.state.valid.value = b
        }
    }
    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                navigate(YoungConfirmRelationshipFragmentDirections.actionYoungConfirmRelationshipFragmentToYoungContactDetailsFragment())
            }
            R.id.edit_query -> {

            }
        }
    }
}
