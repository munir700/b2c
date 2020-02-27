package co.yap.modules.dashboard.more.home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.home.interfaces.IInviteFriend
import co.yap.modules.dashboard.more.home.viewmodels.InviteFriendViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.SHARE_ADJUST_LINK
import co.yap.yapcore.managers.MyUserManager

class InviteFriendFragment : BaseBindingFragment<IInviteFriend.ViewModel>(), IInviteFriend.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_invite_friend

    override val viewModel: IInviteFriend.ViewModel
        get() = ViewModelProviders.of(this).get(InviteFriendViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.tbIvClose -> {
                activity?.finish()
            }
            R.id.btnShare -> {
                shareInfo()
            }
        }
    }

    private fun shareInfo() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        // not set because ios team is not doing this.
        //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, viewModel.state.title.get())
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getBody())
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }

    private fun getBody(): String {
        val name = MyUserManager.user?.currentCustomer?.customerId
//        SHARE_ADJUST_LINK = "https://app.adjust.com/abc123?deep_link=adjustExample%3A%2F%2F"
//        SHARE_ADJUST_LINK = "http://www.google.com/search?digitify"
//        SHARE_ADJUST_LINK = "https://digitify.atlassian.net/browse/YH-57"
        SHARE_ADJUST_LINK = "https://digitify.atlassian.net/jira/people/5c46bf494c070827c2a1a4a0"
//        SHARE_ADJUST_LINK = "https://app.adjust.com/$name?deep_link=adjustExample%3A%2F%2F"


        return getString(Strings.screen_invite_friend_display_text_share_url).format(
            "www.apple.com",
            Constants.SHARE_ADJUST_LINK

        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }
}