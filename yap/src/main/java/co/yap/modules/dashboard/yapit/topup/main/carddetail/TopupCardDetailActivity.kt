package co.yap.modules.dashboard.yapit.topup.main.carddetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.models.CardInfo
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity

class TopupCardDetailActivity : BaseBindingActivity<ITopUpCardDetail.ViewModel>() {

    companion object {
        const val key = "card"
        fun getIntent(context: Context, card: CardInfo): Intent {
            val intent = Intent(context, TopupCardDetailActivity::class.java)
            intent.putExtra(key, card)
            return intent
        }
    }
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_topup_card_detail
    override val viewModel: ITopUpCardDetail.ViewModel
        get() = ViewModelProviders.of(this).get(TopUpCardDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        val card: CardInfo = intent.getParcelableExtra(key)
        viewModel.state.cardInfo.set(card)
    }


    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.state.isCardDeleted.observe(this, Observer {
            when (it) {
                true -> onCardDeleted()
            }
        })
    }

    private fun onCardDeleted() {
        setData()
        finish()
    }

    private fun setData() {
        val returnIntent = Intent()
        returnIntent.putExtra("card", viewModel.state.cardInfo.get())
        returnIntent.putExtra("isCardDeleted", true)
        setResult(Activity.RESULT_OK, returnIntent)
    }
    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.IvClose -> finish()
            R.id.tvRemoveCard -> viewModel.onRemoveCard(viewModel.state.cardInfo.get()!!.cardId)
        }
    }
}