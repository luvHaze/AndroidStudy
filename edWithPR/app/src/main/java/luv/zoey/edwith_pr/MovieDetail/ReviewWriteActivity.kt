package luv.zoey.edwith_pr.MovieDetail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_review_write.*
import luv.zoey.edwith_pr.MovieDetail.ReviewData.MovieReviewDTO
import luv.zoey.edwith_pr.R
import kotlin.collections.ArrayList

class ReviewWriteActivity : AppCompatActivity() {

    val WRITE_SUCCESS : Int = 2020
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)

        val movieName = intent.extras?.get("movieName").toString()
        val movieID = intent.extras?.getInt("movieID")
        movieName_textView_RWA.text = movieName

        writeMovieReview_button_RWA.setOnClickListener {
            var reviewDTO = MovieReviewDTO(
                movieID!!,                               // ID
                getString(R.string.userNickName),                                // NAME
                movieName.toInt(),                          // MOVIE NAME
                null,                            // IMAGE URL
                System.currentTimeMillis().toString(),      // TIME
                null,                              // TIME STAMP
                ratingBar_RWA.rating,                       // RATINGBAR
                writeReview_editText_RWA.text.toString(),   // CONTENT
                0                                 // RECOMMEND
            )

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("objReview", reviewDTO)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()

        }
    }


}
