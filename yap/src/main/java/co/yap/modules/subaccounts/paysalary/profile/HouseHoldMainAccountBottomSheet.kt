/*
package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.yap.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_house_hould_main_account.view.*

class HouseHoldMainAccountBottomSheet(
    private val mListener: HouseHoldMainAccountBottomSheetClick
) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.bottom_sheet_house_hould_main_account, container, false)
        view.tvSubscription.setOnClickListener { mListener.onSubscriptionClick() }
        view.tvSalaryStatements.setOnClickListener { mListener.salaryStatementClick() }
        return view
    }

}*/
