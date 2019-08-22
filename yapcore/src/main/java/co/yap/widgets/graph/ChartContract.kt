package co.yap.widgets.graph

import co.yap.widgets.graph.data.AxisType
import co.yap.widgets.graph.data.Frame
import co.yap.widgets.graph.data.Label
import com.db.williamchart.data.DataPoint

interface ChartContract {

    interface View {

        fun drawLabels(xLabels: List<Label>)

        fun drawData(innerFrame: Frame, entries: List<DataPoint>)

        fun postInvalidate()

        fun drawDebugFrame(
            outerFrame: Frame,
            innerFrame: Frame,
            labelsFrame: List<Frame>
        )
    }

    interface Renderer {

        fun preDraw(
            width: Int,
            height: Int,
            paddingLeft: Int,
            paddingTop: Int,
            paddingRight: Int,
            paddingBottom: Int,
            axis: AxisType,
            labelsSize: Float
        ): Boolean

        fun draw()

        fun render(entries: LinkedHashMap<String, Float>)

        fun anim(entries: LinkedHashMap<String, Float>, animation: ChartAnimation)
    }
}