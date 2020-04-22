package luv.zoey.edwith_pr.Review

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import luv.zoey.edwith_pr.*
import luv.zoey.edwith_pr.MainMenu.AppHelper
import luv.zoey.edwith_pr.Review.Data.MovieDetailDTO
import luv.zoey.edwith_pr.Review.Data.ResponseDTO
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // 어뎁터로 보낼 데이터


    private val REQUEST_CONTENT: Int = 1010
    private var isGoodButtonClicked = false
    private var isBadButtonClicked = false
    private var dataList: ArrayList<ReviewItem> = arrayListOf()
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var movieInfo: ArrayList<MovieDetailDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var requestQueue = Volley.newRequestQueue(applicationContext)

        // RecyclerView 사용법 4. 리싸이클러 뷰 방향설정
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        readMovieReview_recyclerView.layoutManager = layoutManager

        // RecyclerView 사용법 4. 리싸이클러 뷰에 어뎁터 설정해주기 끝.
        reviewAdapter = ReviewAdapter(dataList)
        readMovieReview_recyclerView.adapter = reviewAdapter

        var movie_id: String = intent.extras!!.getString("moive_id").toString()

        sendRequest(movie_id)

    }

    private fun sendRequest(movieId: String) {

        //var url = "${R.string.movieDetailRequest}${movieId}"
        var url = "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovie?id=1"

        try {

            var request = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener {

                    proccessResponse(it)
                },
                Response.ErrorListener {
                    Log.d("Error_RequestDetail", it.toString())
                }
            )

            //이전 결과 있더라도 새로 추가해주는 코드
            request.setShouldCache(false)
            // 만든 리퀘스트를 리퀘스트 큐에 넣어준다.
            AppHelper.requestQueue.add(request)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun proccessResponse(response: String?) {
        var gson = Gson()
        var processData: ResponseDTO = gson.fromJson(response, ResponseDTO::class.java)
        //TODO 고쳐야함
        movieInfo = processData.result
        pageSetUp()
    }

    private fun pageSetUp() {

        Glide.with(this).load(movieInfo[0].image).into(moviePicture_imgview)
    }

    // finish()가 전달 되어야지 실행이 된다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            var contentData = data?.extras?.getString("reviewData")
            var ratingData = data?.extras?.getFloat("ratingData")
            dataList.add(
                ReviewItem(
                    contentData!!,
                    ratingData!!
                )
            )
        }
        movieScore_RatinBar.rating = reviewAdapter.reutrnRating()
        Counter_movieScore.text = reviewAdapter.reutrnRating().toString()
    }

    // OnClickListener 인터페이스를 적용
    // onClick 메소드 안에 모든 버튼의 동작을 모아둔다.
    override fun onClick(v: View?) {
        when (v?.id) {
            // 작성하기 버튼 눌렀을 시
            R.id.writeMovieReview_button -> {
                val writeReview_Intent = Intent(this, ReviewWriteActivity::class.java)
                writeReview_Intent.putExtra("movieName", movieName_textview.text.toString())
                startActivityForResult(writeReview_Intent, REQUEST_CONTENT)
            }

            // 모두보기 버튼 눌렀을 시
            R.id.readAllMovieReview_button -> {
                val readAllReview_Intent = Intent(this, ReviewReadActivity::class.java)
                readAllReview_Intent.putParcelableArrayListExtra("content", dataList)
                startActivity(readAllReview_Intent)
            }

            //좋아요 버튼 눌렀을 시
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

            //싫어요 버튼 눌렀을 시
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
