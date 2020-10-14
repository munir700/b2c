package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentPhoneContactsBinding
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragment
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragmentDirections
import co.yap.modules.dashboard.yapit.y2y.home.yapcontacts.YapContactsAdaptor
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.networking.customers.requestdtos.Contact
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.Utils.getAdjustURL
import co.yap.yapcore.helpers.Utils.getBody
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager


class PhoneContactFragment : Y2YBaseFragment<IPhoneContact.ViewModel>(),
    InviteBottomSheet.OnItemClickListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_phone_contacts
    override val viewModel: PhoneContactViewModel
        get() = ViewModelProviders.of(this).get(PhoneContactViewModel::class.java)

    lateinit var adaptor: YapContactsAdaptor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
        initComponents()
        setObservers()
        viewModel.getY2YBeneficiaries()
    }

    private fun initComponents() {
        adaptor =
            YapContactsAdaptor(mutableListOf())
        getBinding().recycler.adapter = adaptor
        adaptor.setItemListener(listener)
    }

    private fun initState() {
        viewModel.getState().observe(this, Observer { state ->
            if (viewModel.listIsEmpty()) {
                getBinding().recycler.visibility = View.GONE
                getBinding().tvContactListDescription.visibility = View.GONE
                getBinding().txtError.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                getBinding().progressBar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
                if (state == PagingState.DONE || state == PagingState.ERROR) { // error type handling
                    viewModel.parentViewModel?.yapContactLiveData?.postValue(mutableListOf())
                }
            } else {
                getBinding().txtError.visibility = View.GONE
                getBinding().progressBar.visibility = View.GONE
                getBinding().recycler.visibility = View.VISIBLE
                getBinding().tvContactListDescription.visibility = View.VISIBLE
                viewModel.parentViewModel?.yapContactLiveData?.postValue(viewModel.phoneContactLiveData.value?.filter { it.yapUser!! })
            }
        })
    }

    private fun setObservers() {
        viewModel.phoneContactLiveData.observe(this, Observer {
            adaptor.setList(it)
            getBinding().tvContactListDescription.visibility =
                if (it.isEmpty()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            getBinding().txtError.text =
                if (viewModel.parentViewModel?.isSearching?.value!!) "No result" else Translator.getString(
                    requireContext(),
                    Strings.screen_y2y_display_text_no_contacts
                )
        })
        viewModel.parentViewModel?.searchQuery?.observe(this, Observer {
            adaptor.filter.filter(it)
        })

        viewModel.parentViewModel?.isSearching?.value?.let {
            if (it)
                adaptor.filterCount.observe(this, Observer { count ->
                    getBinding().tvContactListDescription.visibility =
                        if (count == 0) View.GONE else View.VISIBLE
                    getBinding().txtError.visibility =
                        if (count == 0 && viewModel.getState().value != PagingState.LOADING) View.VISIBLE else View.GONE
                    getBinding().txtError.text =
                        if (viewModel.parentViewModel?.isSearching?.value!!) "No result" else Translator.getString(
                            requireContext(),
                            Strings.screen_y2y_display_text_no_contacts
                        )
                })
        }
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.userPackageType -> {

                }
                R.id.tvInvite -> {
                    sendInvite((data as Contact))
                }
                R.id.lyContact -> {
                    if (MyUserManager.user?.otpBlocked == true && data is Contact && data.yapUser == false) {
                        showToast(Utils.getOtpBlockedMessage(requireContext()))
                    } else {
                        if (data is Contact && data.yapUser == true && data.accountDetailList != null && data.accountDetailList?.isNotEmpty() == true) {
                            if (parentFragment is YapToYapFragment) {
                                (parentFragment as YapToYapFragment).findNavController().navigate(
                                    YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                                        data.beneficiaryPictureUrl ?: "",
                                        data.accountDetailList?.get(0)?.accountUuid ?: "",
                                        data.title ?: "",
                                        pos
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun sendInvite(contact: Contact) {
        shareInfo(contact)

        /*    this.fragmentManager?.let {
                val inviteFriendBottomSheet = InviteBottomSheet(this, contact)
                inviteFriendBottomSheet.show(it, "")
            }*/
    }

    private fun shareInfo(contact: Contact) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getBody(requireContext(), contact))
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }

    override fun onClick(viewId: Int, data: Any) {
      /*  if (data is Contact)
            when (viewId) {
                R.id.tvChooseEmail -> inviteViaEmail(data)
                R.id.tvChooseSMS -> inviteViaSms(data)
                R.id.tvChooseWhatsapp -> inviteViaWhatsapp(data)
            }*/
    }
/*    private fun inviteViaWhatsapp(contact: Contact) {
        val url =
            "https://api.whatsapp.com/send?phone=${Utils.getFormattedPhoneNumber(
                requireContext(),
                "${contact.countryCode}${contact.mobileNo!!}"
            )}&text=${Utils.getBody(requireContext(), contact)}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun inviteViaEmail(contact: Contact) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", contact.email, null))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, Utils.getBody(requireContext(), contact))
        startActivity(Intent.createChooser(intent, "Send mail..."))
    }

    private fun inviteViaSms(contact: Contact) {
        val uri = Uri.parse(
            "smsto:${Utils.getFormattedPhoneNumber(
                requireContext(),
                "${contact.countryCode}${contact.mobileNo}"
            )}"
        )
        val it = Intent(Intent.ACTION_SENDTO, uri)
        it.putExtra("sms_body", Utils.getBody(requireContext(), contact))
        startActivity(it)
    }*/

    private fun getBinding(): FragmentPhoneContactsBinding {
        return (viewDataBinding as FragmentPhoneContactsBinding)
    }
}