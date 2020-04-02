package luv.zoey.survival_pr2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                puase()
            }
        }

        resetFab.setOnClickListener {
            reset()
        }

        labButton.setOnClickListener {
            recordLapTime()
        }
    }

    private fun start() {
        startFab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                miliTextView.text = "$milli"
            }
        }
    }

    private fun puase() {
        startFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun recordLapTime(){
        val lapTime=this.time
        val textView = TextView(this)
        textView.text="$lap LAB : ${lapTime/100}.${lapTime%100}"

        lapLayout.addView(textView,0)
        lap++
    }

    private fun reset(){
        timerTask?.cancel()

        //모든 변수 초기화
        time=0
        isRunning=false
        startFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text="0"
        miliTextView.text="00"

        lapLayout.removeAllViews()
        lap=1
    }
}
