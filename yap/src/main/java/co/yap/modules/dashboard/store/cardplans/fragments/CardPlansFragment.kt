package co.yap.modules.dashboard.store.cardplans.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navOptions
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlansViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener

class CardPlansFragment : CardPlansBaseFragment<ICardPlans.ViewModel>(), ICardPlans.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_card_plans

    override val viewModel: ICardPlans.ViewModel
        get() = ViewModelProviders.of(this).get(CardPlansViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cardAdapter.onItemClickListener = object :
            OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                clickOnCardPlan(view, data, pos)
            }
        }
    }

    private fun clickOnCardPlan(view: View, data: Any, pos: Int) {
        if (data is CardPlans) {
            when (data.id) {
                Constants.PRIME_CARD_PLAN -> {
                    navigate(destinationId = R.id.action_cardPlansFragment_to_primeCardFragment,
                        args = bundleOf(
                            "title" to data.title,
                            "icon" to data.cardIcon,
                            "description" to data.description
                        ),
                        navOptions = navOptions {
                            anim {
                                enter = co.yap.yapcore.R.anim.slide_in_from_bottom
                                exit = co.yap.yapcore.R.anim.slide_out_to_bottom
                                popEnter = co.yap.yapcore.R.anim.slide_in_left
                                popExit = co.yap.yapcore.R.anim.slide_out_right
                            }
                        })

                }
                Constants.METAL_CARD_PLAN -> {
                    navigate(destinationId = R.id.action_cardPlansFragment_to_metalCardFragment,
                        args = bundleOf(
                            "title" to data.title,
                            "icon" to data.cardIcon,
                            "description" to data.description
                        ),
                        navOptions = navOptions {
                            anim {
                                enter = co.yap.yapcore.R.anim.slide_in_from_bottom
                                exit = co.yap.yapcore.R.anim.slide_out_to_bottom
                                popEnter = co.yap.yapcore.R.anim.slide_in_left
                                popExit = co.yap.yapcore.R.anim.slide_out_right
                            }
                        })
                }
            }
        }

    }

}