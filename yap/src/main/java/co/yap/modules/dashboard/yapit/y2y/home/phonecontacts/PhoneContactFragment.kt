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
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.interfaces.OnItemClickListener


class PhoneContactFragment : Y2YBaseFragment<IPhoneContact.ViewModel>(),
    InvitePhoneContactBottomSheet.OnItemClickListener {


    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_phone_contacts
    private lateinit var inviteFriendBottomSheet: InvitePhoneContactBottomSheet
    override val viewModel: PhoneContactViewModel
        get() = ViewModelProviders.of(this).get(PhoneContactViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
        initComponents()
        setObservers()
    }


    private fun initComponents() {
        val contactColors = intArrayOf(
            R.drawable.bg_round_light_red,
            R.drawable.bg_round_light_blue,
            R.drawable.bg_round_light_green,
            R.drawable.bg_round_light_orange
        )

        getBinding().recycler.adapter = PhoneContactsAdaptor(contactColors) { viewModel.retry() }
        (getBinding().recycler.adapter as PhoneContactsAdaptor).setItemListener(listener)
    }

    private fun initState() {
        //retryBtn.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            if (viewModel.listIsEmpty()) {
                getBinding().recycler.visibility = View.GONE
                getBinding().tvContactListDescription.visibility = View.GONE
                getBinding().txtError.visibility =
                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
                getBinding().progressBar.visibility =
                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
                viewModel.parentViewModel?.yapContactLiveData?.postValue(mutableListOf())
            } else {
                getBinding().txtError.visibility = View.GONE
                getBinding().progressBar.visibility = View.GONE
                getBinding().recycler.visibility = View.VISIBLE
                getBinding().tvContactListDescription.visibility = View.VISIBLE
                (getBinding().recycler.adapter as PhoneContactsAdaptor)?.setState(state)
                viewModel.parentViewModel?.yapContactLiveData?.postValue(viewModel.phoneContactLiveData.value?.filter { it.yapUser!! })
            }
        })
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.phoneContactLiveData.observe(this, Observer {
            (getBinding().recycler.adapter as PhoneContactsAdaptor).submitList(it)
            (getBinding().recycler.adapter as PhoneContactsAdaptor).setState(PagingState.DONE)

        })
        viewModel.parentViewModel?.searchQuery?.observe(this, Observer {
            //(getBinding().recycler.adapter as PhoneContactsAdaptor).filter?.filter(it)
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
                                    , data.accountDetailList?.get(0)?.accountUuid!!, data.title!!
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
            R.id.tvChooseEmail -> inviteViaEmail(contact.email!!)
            R.id.tvChooseSMS -> inviteViaSms(contact.mobileNo!!)
            R.id.tvChooseWhatsapp -> inviteViaWhatsapp(contact.mobileNo!!)
        }
    }

    fun inviteViaWhatsapp(number: String) {
        val url = "https://api.whatsapp.com/send?phone=$number"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun inviteViaEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, getBody())
        startActivity(Intent.createChooser(intent, "Send mail..."))
    }

    fun inviteViaSms(number: String) {
        val uri = Uri.parse("smsto:$number")
        val it = Intent(Intent.ACTION_SENDTO, uri)
        it.putExtra("sms_body", getBody())
        startActivity(it)
    }

    private fun getBody(): String {
        return "App LInk"
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