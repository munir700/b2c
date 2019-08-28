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

// need to fix max value height
//need to work on percentage for height calculation

class DashBoardHomeFragment : BaseFragment<IDashboard.ViewModel>(), IDashboard.View {
    val maxVal: Int = 600

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

        val percentage = dataSet / maxVal * 100
        return Math.round(percentage ).toDouble()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var transactionsList: ArrayList<TransactionModel> = ArrayList()

        transactionsList.add(
            TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                1000.00,
                getAmountPercentage(1000.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 19, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor one",
                500.00,
                getAmountPercentage(500.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor two",
                400.00,
                getAmountPercentage(400.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor three",
                300.00,
                getAmountPercentage(300.00),
                "type",
                "April 12, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor four",
                200.00,
                getAmountPercentage(200.00),
                "type",
                "April 1300, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor five",
                600.00,
                getAmountPercentage(600.00),
                "type",
                "April 1500, 2019",
                "category",
                "AED"
            )
        )
        transactionsList.add(
            TransactionModel(
                "vendor six",
                500.00,
                getAmountPercentage(500.00),
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