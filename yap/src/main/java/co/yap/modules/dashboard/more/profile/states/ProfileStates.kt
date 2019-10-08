package co.yap.modules.dashboard.more.profile.states

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.yapcore.BaseState

class ProfileStates : BaseState(), IProfile.State {

    @get:Bindable
    override var nameInitialsVisibility: Int = VISIBLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.nameInitialsVisibility)

        }

    @get:Bindable
    override var errorBadgeVisibility: Int = GONE
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorBadgeVisibility)

        }

    @get:Bindable
    override var fullName: String = "Logan Rich Pearson"
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)

        }

    @get:Bindable
    override var profilePictureUrl: String =
        "https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg"
        set(value) {
            field = value
            notifyPropertyChanged(BR.profilePictureUrl)
            if (!field.isNullOrEmpty()) {
                nameInitialsVisibility = VISIBLE
                notifyPropertyChanged(BR.nameInitialsVisibility)

            } else {
                nameInitialsVisibility = GONE
                notifyPropertyChanged(BR.nameInitialsVisibility)

            }
        }
}