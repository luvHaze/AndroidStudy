package luv.zoey.survival_pr1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sharedPreference 사용
        loadData()

        btn_getResult.setOnClickListener {
            /*  Anko 라이브러리 쓰지 않을경우
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra("weight",edt_weight.text.toString())
            intent.putExtra("height",edt_height.text.toString())
            */
            saveData(edt_height.text.toString().toInt(), edt_weight.text.toString().toInt())
            //Anko 라이브러리 사용하는 경우
            startActivity<ResultActivity>(
                "height" to edt_height.text.toString(),
                "weight" to edt_weight.text.toString()
            )
        }
    }

    private fun saveData(height: Int, weight: Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_WEIGHT", weight)
            .putInt("KEY_HEIGHT", height)
            .apply()

    }

    private fun loadData() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val height = pref.getInt("KEY_HEIGHT", 0)
        val weight = pref.getInt("KEY_WEIGHT", 0)

        if (height != 0 && weight != 0){
            edt_height.setText(height.toString())
            edt_weight.setText(weight.toString())
        }
    }

}
