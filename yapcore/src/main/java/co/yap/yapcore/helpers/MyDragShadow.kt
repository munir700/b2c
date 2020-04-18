package co.yap.yapcore.helpers

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View

internal class MyDragShadow(view: View?, context: Context) :
    View.DragShadowBuilder(view) {
    var shadowWidth = 0
    var shadowHeight = 0
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mContext: Context = context
    override fun onProvideShadowMetrics(
        shadowSize: Point,
        shadowTouchPoint: Point
    ) {
        shadowWidth = view.width
        shadowHeight = view.height
        shadowSize[shadowWidth] = shadowHeight
        shadowTouchPoint[3 * shadowWidth / 4] = 3 * shadowHeight / 4
    }

    override fun onDrawShadow(canvas: Canvas) {
        canvas.drawBitmap(
            getRoundedCornerBitmap(
                getBitmapFromView(view),
                Color.GRAY,
                15f,
                0f,
                mContext
            ), 0f, 0f, null
        )
    }

    private fun getRoundedCornerBitmap(
        bitmap: Bitmap,
        color: Int,
        cornerDips: Float,
        borderDips: Float,
        context: Context
    ): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val borderSizePx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            borderDips,
            context.getResources().getDisplayMetrics()
        )
        val cornerSizePx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            cornerDips,
            context.getResources().getDisplayMetrics()
        )
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        // prepare canvas for transfer
        paint.isAntiAlias = true
        paint.color = -0x1
        paint.style = Paint.Style.FILL
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint)
        // draw bitmap
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        // draw border
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderSizePx
        canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint)
        return output
    }

    private fun getBitmapFromView(view: View): Bitmap { //Define a bitmap with the same size as the view
        val returnedBitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable: Drawable? = view.background
        if (bgDrawable != null) //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas) else  //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        // draw the view on the canvas
        view.draw(canvas)
        //return the bitmap
        return returnedBitmap
    }

}