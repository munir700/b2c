package co.yap.modules.others.helper

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.adaptor.CardStatementsAdaptor
import co.yap.modules.dashboard.home.status.NotificationProgressStatus
import co.yap.networking.transactions.responsedtos.CardStatement
import com.github.vipulasri.timelineview.TimelineView
import com.liveperson.infra.utils.Utils.getResources

object BindingHelper {


    @BindingAdapter("adaptorList")
    @JvmStatic
    fun setAdaptor(recycleview: RecyclerView, list: ObservableField<List<CardStatement>>) {
        if (!list.get().isNullOrEmpty())
            if (recycleview.adapter is CardStatementsAdaptor)
                (recycleview.adapter as CardStatementsAdaptor).setList(list.get() ?: emptyList())
    }
//
//    @BindingAdapter("adaptorListBankParams")
//    @JvmStatic
//    fun setAdaptorParam(recycleview: RecyclerView, list: ObservableField<List<BankParams>>) {
//        if (!list.get().isNullOrEmpty())
//            if (recycleview.adapter is AddBeneficiariesAdaptor)
//                (recycleview.adapter as AddBeneficiariesAdaptor).setList(list.get() as List<BankParams>)
//    }


/*
  Dashboard notification status
    */

    @BindingAdapter("status")
    @JvmStatic
    fun setStatus(
        view: androidx.appcompat.widget.AppCompatTextView,
        status: NotificationProgressStatus
    ) {

        when (status) {

            NotificationProgressStatus.IS_COMPLETED -> {

                view.getBackground().setColorFilter(
                    getResources().getColor(R.color.colorOpaquAqua),
                    PorterDuff.Mode.SRC_IN
                )
                view.text = "completed"
            }

            NotificationProgressStatus.IN_PROGRESS -> {
                view.getBackground().setColorFilter(
                    getResources().getColor(R.color.colorOpaquSecondaryOrange),
                    PorterDuff.Mode.SRC_IN
                )
                view.text = "in progress"
            }
            else -> {
                view.getBackground().setColorFilter(
                    getResources().getColor(R.color.transparent),
                    PorterDuff.Mode.SRC_IN
                )
                view.text = ""
            }

        }
    }

    @BindingAdapter(value = ["setMarker", "isNotActive"])
    @JvmStatic
    fun setMarker(view: TimelineView, tint: Int?, isNotActive: Boolean =false) {
        val image: Drawable?
        if (isNotActive) {
            image = ContextCompat.getDrawable(view.context, R.drawable.ic_tick_disabled)
            image?.let {
                view.marker = image
            }
        } else
            tint?.let {
                image = ContextCompat.getDrawable(view.context, R.drawable.ic_tick_enabled)
                image?.let {
                    it.setTintList(ContextCompat.getColorStateList(view.context, tint))
                    view.marker = image
                }
            }
    }
}