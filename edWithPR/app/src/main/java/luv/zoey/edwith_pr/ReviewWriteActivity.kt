package luv.zoey.edwith_pr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_review_write.*
import kotlin.random.Random

class ReviewWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)

       // val defualtname : String = "홍길동"
        val d: String = intent.extras?.get("movieName").toString()
        movieName_textView_RWA.setText(d)

        writeMovieReview_button_RWA.setOnClickListener {

            val intent2 = Intent(this, MainActivity::class.java)
            intent2.putExtra("reviewData", writeReview_editText_RWA.text.toString())
            startActivity(intent2)
        }
    }


}
