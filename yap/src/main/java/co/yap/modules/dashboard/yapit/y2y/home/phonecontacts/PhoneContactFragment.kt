package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager


class PhoneContactFragment : Y2YBaseFragment<IPhoneContact.ViewModel>(),
    InvitePhoneContactBottomSheet.OnItemClickListener {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_phone_contacts
    private lateinit var inviteFriendBottomSheet: InvitePhoneContactBottomSheet
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
        adaptor = YapContactsAdaptor(mutableListOf())
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
                if (state == PagingState.DONE) viewModel.parentViewModel?.yapContactLiveData?.postValue(
                    mutableListOf()
                )
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
        viewModel.clickEvent.observe(this, observer)
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
        adaptor.filterCount.observe(this, Observer {

            getBinding().tvContactListDescription.visibility =
                if (it == 0) View.GONE else View.VISIBLE

            getBinding().txtError.visibility = if (it == 0) View.VISIBLE else View.GONE
            getBinding().txtError.text =
                if (viewModel.parentViewModel?.isSearching?.value!!) "No result" else Translator.getString(
                    requireContext(),
                    Strings.screen_y2y_display_text_no_contacts
                )
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.userPackageType -> {

                }
                R.id.tvInvite -> {
                    sendInvite((data as Contact))
//                    Utils.shareText(requireContext(), getBody())
                }
                R.id.lyContact -> {
                    if (data is Contact && data.yapUser!! && data.accountDetailList != null && data.accountDetailList?.isNotEmpty()!!) {
                        if (parentFragment is YapToYapFragment) {
                            (parentFragment as YapToYapFragment).findNavController().navigate(
                                YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                                    data.beneficiaryPictureUrl!!
                                    ,
                                    data.accountDetailList?.get(0)?.accountUuid!!,
                                    data.title!!,
                                    pos
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendInvite(contact: Contact) {
        //TODO: Add check if whatsapp exist or not if not then hide whatsapp text
        inviteFriendBottomSheet = InvitePhoneContactBottomSheet(this, contact)
        inviteFriendBottomSheet.show(this.fragmentManager!!, "")

    }

    override fun onClick(viewId: Int, contact: Contact) {

        when (viewId) {
            R.id.tvChooseEmail -> inviteViaEmail(contact)
            R.id.tvChooseSMS -> inviteViaSms(contact)
            R.id.tvChooseWhatsapp -> inviteViaWhatsapp(contact)
        }
    }

    fun inviteViaWhatsapp(contact: Contact) {
        val url =
            "https://api.whatsapp.com/send?phone=${Utils.getFormattedPhoneNumber(
                requireContext(),
                "${contact.countryCode}${contact.mobileNo!!}"
            )}&text=${getBody()}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun inviteViaEmail(contact: Contact) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", contact.email, null))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, getBody())
        startActivity(Intent.createChooser(intent, "Send mail..."))
    }

    fun inviteViaSms(contact: Contact) {
        val uri = Uri.parse("smsto:${contact.mobileNo}")
        val it = Intent(Intent.ACTION_SENDTO, uri)
        it.putExtra("sms_body", getBody())
        startActivity(it)
    }

    private fun getBody(): String {
        val appShareUrlIOS = "itms-apps://itunes.apple.com/app/id1024941703"
        val appShareUrlAndroid = "https://play.google.com/store/apps/details?id=co.yap"
        return getString(Strings.common_display_text_y2y_share).format(
            MyUserManager.user!!.currentCustomer.getFullName(),
            appShareUrlIOS,
            appShareUrlAndroid
        )
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgStoreShopping -> {
            }
        }
    }

    private fun getBinding(): FragmentPhoneContactsBinding {
        return (viewDataBinding as FragmentPhoneContactsBinding)
    }
}