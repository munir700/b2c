package co.yap.widgets.pieview.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import co.yap.networking.transactions.responsedtos.billpayment.BillLineChartHistory
import co.yap.widgets.pieview.*
import co.yap.widgets.tooltipview.TooltipView
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager
import kotlinx.android.synthetic.main.item_bill_account_details_tooltip_view.view.*
import java.util.*


@SuppressLint("ViewConstructor")
class ToolTipView2(context: Context?, layoutResource: Int, val dataSet: MutableList<BillLineChartHistory>) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private var tooltip: TooltipView = findViewById(R.id.tooltip)
    private var ARROW_SIZE = tooltip.arrowHeight
    private val CIRCLE_OFFSET = 10f;

    init {
        tooltip.arrowView = findViewById(R.id.arrowView)
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        if (e is CandleEntry) {
            tvContent.text = Utils.formatNumber(e.high, 0, true)
        } else {
            if (!dataSet.isNullOrEmpty()) {
                addToolTip(dataSet[highlight.x.toInt()])
            }
        }
        super.refreshContent(e, highlight)
    }

    private fun addToolTip(data: BillLineChartHistory) {
        val text = String.format(
                Locale.getDefault(),
                "%s \nAED %s",
                data.billDate,
                data.amount.toString()
                        .toFormattedCurrency(showCurrency = false,
                                currency = SessionManager.getDefaultCurrency())
        )
        tooltip.apply {
            visibility = View.VISIBLE
            this.bringToFront()
            this.tvContent.text = data.billDate?.let {
                SpannableString(text).apply {
                    setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(context, R.color.greyDark)),
                            0,
                            text.substring(0, text.indexOf("\n")).length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
    }

    private fun tooltipview(view: View?) {
        view?.let {
            tooltip?.apply {
                val viewPosition = IntArray(2)
                val arrowViewPosition = IntArray(2)
                it.getLocationInWindow(viewPosition)
                tooltip?.arrowView?.getLocationInWindow(arrowViewPosition)
                val screen = DisplayMetrics()
                (context as Activity).windowManager.defaultDisplay.getMetrics(screen)
                var rightPadding = 0
                if (viewPosition[0] + this.width >= screen.widthPixels) {
                    translationX =
                            screen.widthPixels.toFloat() - this.width - rightPadding / 2

                    // Adjust position of arrow of tooltip
                    arrowX = viewPosition[0] - x + (it.width / 2) - rightPadding / 2
                    tooltip?.arrowView?.translationX =
                            ((viewPosition[0].toFloat() - (it.width / 2))) + context.dimen(R.dimen.tooltip_default_corner_radius) - rightPadding / 2 // translationX-it.width / 2//viewPosition[0] - x + (it.width / 2)
                } else {
                    val viewWidth = it.width + (context.dimen(R.dimen.margin_one_dp) * 2)
                    if ((viewPosition[0] - viewWidth) > 0) {
                        translationX =
                                viewPosition[0].toFloat() - tooltip?.arrowView?.width!!//context.dimen(R.dimen._2sdp)
                        arrowX =
                                viewPosition[0] - x + (it.width / 2)
                        tooltip?.arrowView?.translationX =
                                viewPosition[0].toFloat() - (it.width / 2) + context.dimen(R.dimen.tooltip_default_corner_radius)
                    } else {
                        translationX = 0f
                        arrowX = 0f
                        tooltip?.arrowView?.translationX = viewPosition[0].toFloat()
                    }
                }
                addToolTipDelay(10) {
                    y = (it.y - this.height) - (tooltip?.arrowView?.height?.div(2)
                            ?: context.dimen(R.dimen._6sdp))
                }
            }
        }

    }


    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        val offset: MPPointF = offset
        val chart = chartView
        val width = width.toFloat()
        val height = height.toFloat()
        // posY \posX 指的是markerView左上角点在图表上面的位置
//处理Y方向
        if (posY <= height + ARROW_SIZE) { // 如果点y坐标小于markerView的高度，如果不处理会超出上边界，处理了之后这时候箭头是向上的，我们需要把图标下移一个箭头的大小
            offset.y = ARROW_SIZE.toFloat()
        } else { //否则属于正常情况，因为我们默认是箭头朝下，然后正常偏移就是，需要向上偏移markerView高度和arrow size，再加一个stroke的宽度，因为你需要看到对话框的上面的边框
            offset.y = -height - ARROW_SIZE - 5 // 40 arrow height   5 stroke width
        }
        //处理X方向，分为3种情况，1、在图表左边 2、在图表中间 3、在图表右边
//
        if (posX > chart.width - width) { //如果超过右边界，则向左偏移markerView的宽度
            offset.x = -width
        } else { //默认情况，不偏移（因为是点是在左上角）
            offset.x = 0f
            if (posX > width / 2) { //如果大于markerView的一半，说明箭头在中间，所以向右偏移一半宽度
                offset.x = -(width / 2)
            }
        }
        return offset
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
        val whitePaint = Paint() //绘制底色白色的画笔
        whitePaint.color = Color.BLUE
        whitePaint.style = Paint.Style.FILL
        whitePaint.strokeWidth = 1f
        val chart = chartView
        val width = width.toFloat()
        val height = height.toFloat()
        val offset = getOffsetForDrawingAtPoint(posX, posY)
        val saveId = canvas.save()
        val path: Path
        if (posY < height + tooltip.arrowHeight) { //处理超过上边界
            path = Path()
            path.moveTo(0f, 0f)
            if (posX > chart.width - width) { //超过右边界
                path.lineTo(width - ARROW_SIZE, 0f)
                path.lineTo(width, -ARROW_SIZE + CIRCLE_OFFSET)
                path.lineTo(width, 0f)
            } else {
                if (posX > width / 2) { //在图表中间
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0f)
                    path.lineTo(width / 2, -ARROW_SIZE + CIRCLE_OFFSET)
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0f)
                } else { //超过左边界
                    path.lineTo(0f, -ARROW_SIZE + CIRCLE_OFFSET)
                    path.lineTo((0 + ARROW_SIZE).toFloat(), 0f)
                }
            }
            path.lineTo(0 + width, 0f)
            path.lineTo(0 + width, 0 + height)
            path.lineTo(0f, 0 + height)
            path.lineTo(0f, 0f)
            path.offset(posX + offset.x, posY + offset.y)
        } else {
            path = Path()
            path.moveTo(0f, 0f)
            path.lineTo(0 + width, 0f)
            path.lineTo(0 + width, 0 + height)
            if (posX > chart.width - width) {
                path.lineTo(width, height + ARROW_SIZE - CIRCLE_OFFSET)
                path.lineTo(width - ARROW_SIZE, 0 + height)
                path.lineTo(0f, 0 + height)
            } else {
                if (posX > width / 2) {
                    path.lineTo(width / 2 + ARROW_SIZE / 2, 0 + height)
                    path.lineTo(width / 2, height + ARROW_SIZE - CIRCLE_OFFSET)
                    path.lineTo(width / 2 - ARROW_SIZE / 2, 0 + height)
                    path.lineTo(0f, 0 + height)
                } else {
                    path.lineTo((0 + ARROW_SIZE).toFloat(), 0 + height)
                    path.lineTo(0f, height + ARROW_SIZE - CIRCLE_OFFSET)
                    path.lineTo(0f, 0 + height)
                }
            }
            path.lineTo(0f, 0f)
            path.offset(posX + offset.x, posY + offset.y)
        }

        // translate to the correct position and draw
        canvas.drawPath(path, whitePaint)
        canvas.translate(posX + offset.x, posY + offset.y)
        draw(canvas)
        canvas.restoreToCount(saveId)
    }

    private fun addToolTipDelay(delay: Long, process: () -> Unit) {
        Handler().postDelayed({
            process()
        }, delay)
    }
}