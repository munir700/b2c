package co.yap.modules.dashboard.more.help.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHelpSupportBinding
import co.yap.modules.dashboard.more.bankdetails.activities.BankDetailActivity
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.help.adaptor.HelpSupportAdaptor
import co.yap.modules.dashboard.more.help.interfaces.IHelpSupport
import co.yap.modules.dashboard.more.help.viewmodels.HelpSupportViewModel
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener


class HelpSupportFragment : MoreBaseFragment<IHelpSupport.ViewModel>(), IHelpSupport.View {

    lateinit var adapter: HelpSupportAdaptor

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_help_support

    override val viewModel: IHelpSupport.ViewModel
        get() = ViewModelProviders.of(this).get(HelpSupportViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initComponents()
        setupRecycleView()
    }


    private fun initComponents() {
//        getBinding().tvName.text =
//            MyUserManager.user?.customer?.firstName + " " + MyUserManager.user?.customer?.lastName
//
//        val ibanSpan = SpannableString("IBAN " + MyUserManager.user?.iban)
//        val bicSpan = SpannableString("BIC " + MyUserManager.user?.accountNo)
//
//        getBinding().tvIban.text = Utils.setSpan(
//            0,
//            4,
//            ibanSpan,
//            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
//        )
//        getBinding().tvBic.text = Utils.setSpan(
//            0,
//            3,
//            bicSpan,
//            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
//        )
    }

    private fun setupRecycleView() {
//        adapter = HelpSupportAdaptor(requireContext(), viewModel.getMoreOptions())
//        getBinding().recyclerOptions.adapter = adapter
//        adapter.allowFullItemClickListener = true
//        adapter.setItemListener(listener)
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
            }
            R.id.tvName -> {
            }
            R.id.tvIban -> {
            }
            R.id.btnBankDetails -> {
                startActivity(BankDetailActivity.newIntent(requireContext()))
            }
        }
    }

    override fun getBinding(): FragmentHelpSupportBinding {
        return viewDataBinding as FragmentHelpSupportBinding
    }
}