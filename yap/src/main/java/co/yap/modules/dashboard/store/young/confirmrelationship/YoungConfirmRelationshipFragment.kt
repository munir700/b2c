package co.yap.modules.dashboard.store.young.confirmrelationship

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungConfirmRelationshipBinding
import co.yap.translation.Strings

import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_young_confirm_relationship.*

@AndroidEntryPoint
class YoungConfirmRelationshipFragment :
    BaseNavViewModelFragmentV2<FragmentYoungConfirmRelationshipBinding, IYoungConfirmRelationship.State, YoungConfirmRelationshipVM>(),
    AdapterView.OnItemSelectedListener {
    override fun getBindingVariable() = BR.viewModel
    override val viewModel: YoungConfirmRelationshipVM by viewModels()
    override fun getLayoutId() = R.layout.fragment_young_confirm_relationship
    override fun toolBarVisibility() = true
    override fun getToolBarTitle() =
        getString(Strings.screen_young_confirm_relationship_toolbar_text)

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        scTermscondition.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            viewModel.state.switchChecked.value = isChecked
            viewModel.state.valid.value = isValidToMove()
        }
        msRelationShip.onItemSelectedListener = this
    }

    private fun isValidToMove(): Boolean? {
        if (viewModel.state.relationSelected.value != 0 && viewModel.state.switchChecked.value == true) return true
        return false
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                navigate(YoungConfirmRelationshipFragmentDirections.actionYoungConfirmRelationshipFragmentToYoungContactDetailsFragment())
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.state.relationSelected = MutableLiveData(msRelationShip.selectedItemPosition)
        viewModel.state.valid.value = isValidToMove()
    }
}
