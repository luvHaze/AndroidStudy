package luv.zoey.edwith_pr

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // 어뎁터로 보낼 데이터
    var dataName: MutableList<String> = arrayListOf()
    var dataReview: MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isGoodButtonClicked = false
        var isBadButtonClicked = false

        // RecyclerView 사용법 4. 리싸이클러 뷰 방향설정
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        readMovieReview_recyclerView.setLayoutManager(layoutManager)
        val adapter : ReviewAdapter =  ReviewAdapter()
        // RecyclerView 사용법 4. 리싸이클러 뷰에 어뎁터 설정해주기 끝.
        readMovieReview_recyclerView.adapter = adapter


        adapter.addItem(ReviewItem("1","rtert"))
        adapter.addItem(ReviewItem("2","rtret"))
        adapter.addItem(ReviewItem("3","fgggt"))
        if(intent.extras!=null) {
            var gettingReviewData: String = intent.extras?.get("reviewData").toString()


            Toast.makeText(this, adapter.itemCount.toString(), Toast.LENGTH_LONG).show()
        }

        writeMovieReview_button.setOnClickListener {

            val writeReview_Intent = Intent(this, ReviewWriteActivity::class.java)
            writeReview_Intent.putExtra("movieName", movieName_textview.text.toString())

            startActivity(writeReview_Intent)
        }

        movieGood_Button.setOnClickListener {
            var goodCountTemp = movieGoodCount_textView.text.toString().toInt()
            var badCountTemp = movieBadCount_textView.text.toString().toInt()

            if (isGoodButtonClicked) {
                goodCountTemp -= 1
                isGoodButtonClicked = false
            } else if (isBadButtonClicked) {
                badCountTemp -= 1
                goodCountTemp += 1
                movieBadCount_textView.text = badCountTemp.toString()
                isGoodButtonClicked = true
                isBadButtonClicked = false
            } else {
                goodCountTemp += 1
                isGoodButtonClicked = true
                movieGood_Button.setColorFilter(Color.rgb(155, 155, 0))
            }

            movieGoodCount_textView.setText(goodCountTemp.toString())

            if (isGoodButtonClicked) {
                movieGood_Button.setColorFilter(Color.rgb(255, 165, 2))
                movieBad_Button.setColorFilter(Color.rgb(47, 53, 66))
            } else {
                movieGood_Button.setColorFilter(Color.rgb(47, 53, 66))
            }
        }

        movieBad_Button.setOnClickListener {

            var goodCountTemp = movieGoodCount_textView.text.toString().toInt()
            var badCountTemp = movieBadCount_textView.text.toString().toInt()

            if (isGoodButtonClicked) {
                goodCountTemp -= 1
                badCountTemp += 1
                isGoodButtonClicked = false
                isBadButtonClicked = true
                movieGoodCount_textView.text = goodCountTemp.toString()
            } else if (isBadButtonClicked) {
                badCountTemp -= 1
                isBadButtonClicked = false
            } else {
                badCountTemp += 1
                isBadButtonClicked = true
            }

            movieBadCount_textView.setText(badCountTemp.toString())

            if (isBadButtonClicked) {
                movieBad_Button.setColorFilter(Color.rgb(255, 165, 2))
                movieGood_Button.setColorFilter(Color.rgb(47, 53, 66))
            } else {
                movieBad_Button.setColorFilter(Color.rgb(47, 53, 66))
            }
        }


    }

    override fun onResume() {
        super.onResume()


    }

}
