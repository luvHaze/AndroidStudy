package luv.zoey.edwith_pr

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // 어뎁터로 보낼 데이터

    var dataList: ArrayList<ReviewItem> = arrayListOf()
    val REQUEST_CONTENT: Int = 1010
    var isGoodButtonClicked = false
    var isBadButtonClicked = false
    lateinit var reviewAdapter : ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView 사용법 4. 리싸이클러 뷰 방향설정
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        readMovieReview_recyclerView.layoutManager = layoutManager

        // RecyclerView 사용법 4. 리싸이클러 뷰에 어뎁터 설정해주기 끝.
        reviewAdapter= ReviewAdapter(dataList)
        readMovieReview_recyclerView.adapter = reviewAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            var contentData = data?.extras?.getString("reviewData")
            var ratingData = data?.extras?.getFloat("ratingData")
            dataList.add(ReviewItem(contentData!!,ratingData!!))
        }
        movieScore_RatinBar.rating=reviewAdapter.reutrnRating()
        Counter_movieScore.setText(reviewAdapter.reutrnRating().toString())
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.writeMovieReview_button -> {
                val writeReview_Intent = Intent(this, ReviewWriteActivity::class.java)
                writeReview_Intent.putExtra("movieName", movieName_textview.text.toString())
                startActivityForResult(writeReview_Intent, REQUEST_CONTENT)

            }
            R.id.readAllMovieReview_button ->{
                val readAllReview_Intent = Intent(this,ReviewReadActivity::class.java)
                readAllReview_Intent.putParcelableArrayListExtra("content",dataList)
                startActivity(readAllReview_Intent)
            }

            R.id.movieGood_Button -> {
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

            R.id.movieBad_Button -> {
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

    }

}
