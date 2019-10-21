package co.yap.modules.dashboard.more.home.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMoreHomeBinding
import co.yap.modules.dashboard.more.bankdetails.activities.BankDetailActivity
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.home.adaptor.YapMoreAdaptor
import co.yap.modules.dashboard.more.home.interfaces.IMoreHome
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.modules.dashboard.more.home.viewmodels.MoreHomeViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager


class YapMoreFragment : MoreBaseFragment<IMoreHome.ViewModel>(), IMoreHome.View {

    lateinit var adapter: YapMoreAdaptor

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_more_home

    override val viewModel: IMoreHome.ViewModel
        get() = ViewModelProviders.of(this).get(MoreHomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        setupRecycleView()
    }

    private fun initComponents() {
        getBinding().tvName.text =
            MyUserManager.user?.currentCustomer?.getFullName()

        val ibanSpan = SpannableString("IBAN " + MyUserManager.user?.iban)
        val bicSpan = SpannableString("BIC " + MyUserManager.user?.bank?.swiftCode)

        getBinding().tvIban.text = Utils.setSpan(
            0,
            4,
            ibanSpan,
            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
        )
        getBinding().tvBic.text = Utils.setSpan(
            0,
            3,
            bicSpan,
            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
        )
    }

    private fun setupRecycleView() {
        adapter = YapMoreAdaptor(requireContext(), viewModel.getMoreOptions())
        getBinding().recyclerOptions.adapter = adapter
        adapter.allowFullItemClickListener = true
        adapter.setItemListener(listener)
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is MoreOption) {
                when (data.id) {
                    Constants.MORE_NOTIFICATION -> {

                    }
                    Constants.MORE_LOCATE_ATM -> {
                        openMaps()
                    }
                    Constants.MORE_INVITE_FRIEND -> {

                    }
                    Constants.MORE_HELP_SUPPORT -> {
                        val action =
                            YapMoreFragmentDirections.actionYapMoreToHelpSupportFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun openMaps() {
        //for zoom level z=zoom
        val uri = Uri.parse("geo:3.4241,53.847?q=" + Uri.encode("Rakbank Atm"))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgProfile -> {
                findNavController().navigate(YapMoreFragmentDirections.actionYapMoreToMoreActivity())
            }
            R.id.imgSettings -> {
                findNavController().navigate(YapMoreFragmentDirections.actionYapMoreToMoreActivity())
            }
            R.id.tvName -> {
                findNavController().navigate(YapMoreFragmentDirections.actionYapMoreToMoreActivity())
            }
            R.id.tvNameInitials -> {
                findNavController().navigate(YapMoreFragmentDirections.actionYapMoreToMoreActivity())
            }
            R.id.tvIban -> {
            }
            R.id.btnBankDetails -> {
                startActivity(BankDetailActivity.newIntent(requireContext()))
            }
        }
    }

    override fun getBinding(): FragmentMoreHomeBinding {
        return viewDataBinding as FragmentMoreHomeBinding
    }
}