package co.yap.modules.dashboard.store.cardplans.interfaces

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import co.yap.databinding.FragmentCardPlansBinding
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.CardPlansAdapter
import co.yap.yapcore.IBase
import com.google.android.gms.common.internal.safeparcel.SafeParcelable

interface ICardPlans {
    interface View : IBase.View<ViewModel>{
        fun getBindings(): FragmentCardPlansBinding
        fun navigateToFragment(data: String)
    }
    interface ViewModel : IBase.ViewModel<State>{
        var cardAdapter : CardPlansAdapter
        fun setViewDimensions(percent : Int, view : android.view.View) : ConstraintLayout.LayoutParams
    }
    interface State : IBase.State
}