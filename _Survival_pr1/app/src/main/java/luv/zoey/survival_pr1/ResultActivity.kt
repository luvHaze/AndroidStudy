package luv.zoey.survival_pr1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        val height = intent.getStringExtra("height").toInt()
        val weight =intent.getStringExtra("weight").toInt()

        val bmi=weight/Math.pow(height/100.0,2.0)

        when{
            bmi>=35 -> tv_Result.text="고도 비만"
            bmi>=30 -> tv_Result.text="2단계 비만"
            bmi>=25 -> tv_Result.text="1단계 비만"
            bmi>=23 -> tv_Result.text="과체중"
            bmi>=18.5 -> tv_Result.text="정상"
            else -> tv_Result.text="저체중"
        }

        when{
            bmi >=23.5 -> img_status.setImageResource(R.drawable.bad)
            bmi >=18.5 -> img_status.setImageResource(R.drawable.soso)
            else -> img_status.setImageResource(R.drawable.good)
        }

        toast("$bmi")
    }
}
