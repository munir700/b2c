package co.yap.household.dashboard.more

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseHoldMoreBinding
import co.yap.modules.dashboard.more.cdm.CdmMapFragment
import co.yap.modules.dashboard.more.help.fragments.HelpSupportFragment
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.notifications.main.NotificationsActivity
import co.yap.modules.webview.WebViewFragment
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes

import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.ThemeColorUtils
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.leanplum.Leanplum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_house_hold_more.*
import javax.inject.Inject

@AndroidEntryPoint
class HouseHoldMoreFragment :
    BaseNavViewModelFragmentV2<FragmentHouseHoldMoreBinding, IHouseHoldMore.State, HouseHoldMoreVM>() {
     val adapter: Adapter by lazy {
         Adapter(HouseHoldMoreModule().provideMoreOptions(requireContext()), null)
     }
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_more
    override fun toolBarVisibility() = false
    override val viewModel: HouseHoldMoreVM by viewModels()

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.adapter.set(adapter)
        setHasOptionsMenu(true)
        adapter.onItemClickListener = onItemClickListener
        recyclerView.addItemDecoration(
            SpaceGridItemDecoration(
                dimen(co.yap.R.dimen.margin_normal_large), 2, true
            )
        )
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val option = data as MoreOption
            when (option.id) {
                R.id.more_atm_cdm -> startFragment(CdmMapFragment::class.java.name)
                R.id.more_help_support -> startFragment(HelpSupportFragment::class.java.name)
                R.id.more_notification -> {
                    trackEventWithScreenName(FirebaseEvent.CLICK_NOTIFICATIONS)
                    requireActivity().launchActivity<NotificationsActivity>(requestCode = RequestCodes.REQUEST_NOTIFICATION_FLOW) {
                    }
                }
                R.id.more_terms_condition -> startFragment(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        Constants.PAGE_URL to Constants.URL_TERMS_CONDITION
                    ), showToolBar = true
                )
            }
        }
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.imgSettings, R.id.imgProfile -> launchActivity<MoreActivity>()
            R.id.tvLogOut -> {
                logoutAlert()
            }
        }
    }

    private fun logoutAlert() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(co.yap.R.string.screen_profile_settings_logout_display_text_alert_title))
            .setMessage(getString(co.yap.R.string.screen_profile_settings_logout_display_text_alert_message))
            .setPositiveButton(
                getString(co.yap.R.string.screen_profile_settings_logout_display_text_alert_logout)
            ) { _, _ ->
                viewModel.logout {
                    doLogout()
                }
            }
            .setNegativeButton(
                getString(co.yap.R.string.screen_profile_settings_logout_display_text_alert_cancel),
                null
            )
            .show()
    }

    private fun doLogout() {
        SessionManager.doLogout(requireContext())
        activity?.finish()
    }

    class Adapter @Inject constructor (mValue: MutableList<MoreOption>, navigation: NavController?) :
        BaseRVAdapter<MoreOption, HHMoreItemVM, BaseViewHolder<MoreOption, HHMoreItemVM>>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: HHMoreItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = HHMoreItemVM()
        override fun getVariableId() = BR.viewModel
    }

}
