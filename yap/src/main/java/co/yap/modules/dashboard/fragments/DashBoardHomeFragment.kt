package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.R
import co.yap.modules.dashboard.adapters.DashboardAdapter
import co.yap.modules.dashboard.interfaces.IDashboard
import co.yap.modules.dashboard.viewmodels.DashBoardViewModel
import co.yap.modules.onboarding.models.TransactionModel
import co.yap.yapcore.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashBoardHomeFragment : BaseFragment<IDashboard.ViewModel>(), IDashboard.View {

    override val viewModel: IDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(DashBoardViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    fun getAmountPercentage(dataSet: Double): Double {

        val percentage = dataSet / 2000 * 100
        return Math.round(percentage * 100).toDouble()
//        return (percentage * 100)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var transactionsList: ArrayList<TransactionModel> = ArrayList()

//        var transactionModel : TransactionModel = TransactionModel("vendor one", 5.00, getAmountPercentage(5.00),"type","April 12, 2019","category", "AED")
        transactionsList.add(
            TransactionModel(
                "vendor one",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                4.00,
                getAmountPercentage(4.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                3.00,
                getAmountPercentage(3.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                2.00,
                getAmountPercentage(2.00),
                "type",
                "April 13, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                6.00,
                getAmountPercentage(6.00),
                "type",
                "April 15, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                4.00,
                getAmountPercentage(4.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                3.00,
                getAmountPercentage(3.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                2.00,
                getAmountPercentage(2.00),
                "type",
                "April 13, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                6.00,
                getAmountPercentage(6.00),
                "type",
                "April 15, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                4.00,
                getAmountPercentage(4.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                3.00,
                getAmountPercentage(3.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                2.00,
                getAmountPercentage(2.00),
                "type",
                "April 13, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                6.00,
                getAmountPercentage(6.00),
                "type",
                "April 15, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                4.00,
                getAmountPercentage(4.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                3.00,
                getAmountPercentage(3.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                2.00,
                getAmountPercentage(2.00),
                "type",
                "April 13, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                6.00,
                getAmountPercentage(6.00),
                "type",
                "April 15, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                4.00,
                getAmountPercentage(4.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                3.00,
                getAmountPercentage(3.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                2.00,
                getAmountPercentage(2.00),
                "type",
                "April 13, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                6.00,
                getAmountPercentage(6.00),
                "type",
                "April 15, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                5.00,
                getAmountPercentage(5.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )

        rvTransactionsBarChart.adapter = DashboardAdapter(transactionsList, this!!.activity!!)
        rvTransactionsBarChart.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
    }
}