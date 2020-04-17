package luv.zoey.edwith_pr.Review

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_review_write.*
import luv.zoey.edwith_pr.R

class ReviewWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)

        val d: String = intent.extras?.get("movieName").toString()
        movieName_textView_RWA.setText(d)

        writeMovieReview_button_RWA.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("reviewData", writeReview_editText_RWA.text.toString())
                putExtra("ratingData",ratingBar_RWA.rating)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()

        }
    }


}