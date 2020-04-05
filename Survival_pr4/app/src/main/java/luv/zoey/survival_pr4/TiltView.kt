package luv.zoey.survival_pr4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {

    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()

    private var cX: Float = 0f
    private var cY: Float = 0f

    private var xCoord: Float = 0f
    private var yCoord: Float = 0f

    init {
        greenPaint.color = Color.MAGENTA
        blackPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(cX, cY, 100F, blackPaint) // x, y, 반지름, 색
        canvas?.drawCircle(xCoord+cX, yCoord+cY, 100F, greenPaint)

        canvas?.drawLine(cX - 20F, cY, cX + 20F, cY, blackPaint)
        canvas?.drawLine(cX, cY - 20F, cX, cY + 20F, greenPaint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w / 2f
        cY = h / 2f
    }

    fun onSensorEvent(event: SensorEvent) {
        //화면을 가로로 돌렸으므로 X축과 Y축을 서로 바꿈
        yCoord=event.values[0]*20
        xCoord=event.values[1]*20

        // * onDraaw를 다시 호출하는 메서드
        invalidate()
    }


}