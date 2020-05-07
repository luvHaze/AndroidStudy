package luv.zoey.edwith_pr.MovieDetail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.delete
import kotlinx.android.synthetic.main.activity_main.*
import luv.zoey.edwith_pr.*
import luv.zoey.edwith_pr.AppHelper
import luv.zoey.edwith_pr.MovieDetail.Data.MovieDetailDTO
import luv.zoey.edwith_pr.MovieDetail.Data.ResponseDTO
import luv.zoey.edwith_pr.MovieDetail.ReviewData.MovieReviewDTO
import luv.zoey.edwith_pr.MovieDetail.ReviewData.ResponseReviewDTO
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var movieID: Int = 0
    private val WRITE_CONTENT: Int = 2020
    private var isGoodButtonClicked = false
    private var isBadButtonClicked = false
    private var goodCountTemp: Int = 0
    private var badCountTemp: Int = 0
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var realm: Realm
    private var movieReviewList: ArrayList<MovieReviewDTO> = arrayListOf()
   // private val realm: Realm = Realm.getDefaultInstance()

    val BASE_URL = "http://boostcourse-appapi.connect.or.kr:10000"
    val READ_MOVIE_LIST = "/movie/readMovieList"
    val READ_MOVIE = "/movie/readMovie"
    val READ_REVIEW_LIST = "/movie/readCommentList"
    val CREATE_REVIEW = "/movie/createComment"
    val APPLY_RECOMMEND = "/movie/increaseRecommend"
    val APPLY_LIKE_DISLIKE = "/movie/increaseLikeDisLike"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        // 메인메뉴 인텐트에서 ID부터 받아온다.
        movieID = intent.extras!!.getInt("movieID")
        sendRequest(movieID)

        // RecyclerView 사용법 4. 리싸이클러 뷰 방향설정
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        readMovieReview_recyclerView.layoutManager = layoutManager
        Log.d("movieReviewList", movieReviewList.toString())

    }

    // 1. 액티비티가 만들어지고 영화ID를 이용해 API 로 부터 데이터를 가져오는 메소드 (MovieData, MovieReview) 가져옴
    private fun sendRequest(movieID: Int) {

        var movieInfoURL =
            "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovie?id=$movieID"
        var movieReviewURL =
            "http://boostcourse-appapi.connect.or.kr:10000/movie/readCommentList?id=$movieID"

        Log.d("url", movieInfoURL)
        try {

            var requestMovieInfo = StringRequest(
                Request.Method.GET,
                movieInfoURL,
                Response.Listener {
                    movieDataResponse(it)
                },
                Response.ErrorListener {
                    Log.d("Error_RequestDetail", it.toString())
                }
            )

            var requestReview = StringRequest(
                Request.Method.GET,
                movieReviewURL,
                Response.Listener {
                    movieReviewResponse(it)
                },
                Response.ErrorListener {
                    Log.d("Error_RequestDetail", it.toString())
                }

            )

            //이전 결과 있더라도 새로 추가해주는 코드
            requestMovieInfo.setShouldCache(false)
            requestReview.setShouldCache(false)
            // 만든 리퀘스트를 리퀘스트 큐에 넣어준다.
            AppHelper.requestQueue.add(requestMovieInfo)
            AppHelper.requestQueue.add(requestReview)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // 2.1 MovieData의 요청을 API로부터 응답을 받았을 때 Gson으로 json 처리를 해준다
    private fun movieDataResponse(response: String?) {
        val gson = Gson()
        val processData = gson.fromJson(response, ResponseDTO::class.java)

        var movieInfo = processData.result[0]
        movieDataResponsePageSetting(movieInfo)

        //TODO DATABASE

        realm.executeTransaction {

            //it.insert(movieInfo)
            //Log.d("DATA INSERT ", it.where(MovieDetailDTO::class.java).findAll().toString())
        }

    }

    // json화된 영화정보 데이타를 각 뷰에 설정해 준다.
    private fun movieDataResponsePageSetting(movieInfo: MovieDetailDTO) {

        Glide.with(this).load(movieInfo.image).into(moviePicture_imgview)
        movieName_textview.text = movieInfo.title
        when (movieInfo.grade) {
            12 -> movieGrade_imgView.setImageResource(R.drawable.ic_12)
            15 -> movieGrade_imgView.setImageResource(R.drawable.ic_15)
            19 -> movieGrade_imgView.setImageResource(R.drawable.ic_19)
        }
        movieDay_textview.text = movieInfo.date
        movieType_textview.text = movieInfo.genre
        movieTime_textview.text = "${movieInfo.duration.toString()}분"
        movieGoodCount_textView.text = movieInfo.like.toString()
        movieBadCount_textView.text = movieInfo.dislike.toString()
        movieRating_textView.text = movieInfo.reservation_grade.toString() + "위"
        movieScore_RatinBar.rating = movieInfo.user_rating!!
        movieScore_TextView.text = movieInfo.user_rating.toString()
        movieCountPeople_textView.text = movieInfo.audience.toString() + "명"
        movieStory_textview.text = movieInfo.synopsis
        movieDirector_textView.text = movieInfo.director
        movieActor_textView.text = movieInfo.actor

    }

    // 2.2  MovieReview의 요청을 API로부터 응답을 받았을 때 Gson으로 json 처리를 해준다
    private fun movieReviewResponse(response: String?) {
        val gson = Gson()
        var processData = gson.fromJson(response, ResponseReviewDTO::class.java)

        processData.result.forEach {
            movieReviewList.add(it)
        }

        // 리싸이클러 뷰에 어뎁터 설정해주기
        reviewAdapter = ReviewAdapter(movieReviewList)
        readMovieReview_recyclerView.adapter = reviewAdapter
    }

    // finish()가 전달 되어야지 실행이 된다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                WRITE_CONTENT -> {

                    val reviewDTO = data?.extras?.get("objReview") as MovieReviewDTO

                    //TODO -> API URL으로 내가 쓴 리뷰를 보내줘야 한다.
                    Log.d("checkOBJ", reviewDTO.toString())

                    val storeReviewURL =
                        "http://boostcourse-appapi.connect.or.kr:10000/movie/createComment"

                    val storeReviewRequest = object : StringRequest(
                        Request.Method.POST,
                        storeReviewURL,
                        Response.Listener {
                            Log.d("Post Success", it)
                            movieReviewList.add(reviewDTO)
                        },
                        Response.ErrorListener {
                            Log.d("Post Error", it.toString())
                        }
                    ) {
                        override fun getParams(): HashMap<String, String?> {
                            val params = HashMap<String, String?>()

                            params["id"] = reviewDTO.id.toString()
                            params["writer"] = reviewDTO.writer
                            params["time"] = reviewDTO.time
                            params["rating"] = reviewDTO.rating.toString()
                            params["contents"] = reviewDTO.contents

                            return params
                        }
                    }
                    storeReviewRequest.setShouldCache(false)
                    AppHelper.requestQueue.add(storeReviewRequest)

                }

            }

        }
        movieScore_RatinBar.rating = reviewAdapter.reutrnRating()
        movieScore_TextView.text = reviewAdapter.reutrnRating().toString()
    }

    // OnClickListener 인터페이스를 적용
    // onClick 메소드 안에 모든 버튼의 동작을 모아둔다.
    override fun onClick(v: View?) {
        when (v?.id) {
            // 작성하기 버튼 눌렀을 시
            R.id.writeMovieReview_button -> {
                val writeReview_Intent = Intent(this, ReviewWriteActivity::class.java).apply {
                    putExtra("movieName", movieName_textview.text.toString())
                    putExtra("movieID", movieID)
                }
                startActivityForResult(writeReview_Intent, WRITE_CONTENT)
            }

            // 모두보기 버튼 눌렀을 시
            R.id.readAllMovieReview_button -> {
                val readAllReview_Intent = Intent(this, ReviewReadActivity::class.java)
                readAllReview_Intent.putExtra("MovieList", movieReviewList)
                startActivity(readAllReview_Intent)
            }

            //좋아요 버튼 눌렀을 시
            R.id.movieGood_Button -> {
                var url = "${BASE_URL}${APPLY_LIKE_DISLIKE}"
                var likeyn: String
                goodCountTemp = movieGoodCount_textView.text.toString().toInt()
                badCountTemp = movieBadCount_textView.text.toString().toInt()

                when {
                    // 좋아요버튼이 눌려있는 상태에서 또 눌렀을 때 (중복)
                    isGoodButtonClicked -> {
                        goodCountTemp -= 1
                        isGoodButtonClicked = false
                        movieGood_Button.setColorFilter(Color.rgb(47, 53, 66))
                        movieBad_Button.setColorFilter(Color.rgb(47, 53, 66))
                        likeyn = "N"
                    }
                    // 싫어요버튼이 눌려있는 상태에서 좋아요버튼 누를 때
                    isBadButtonClicked -> {
                        badCountTemp -= 1
                        goodCountTemp += 1
                        movieBadCount_textView.text = badCountTemp.toString()
                        isGoodButtonClicked = true
                        isBadButtonClicked = false
                        movieGood_Button.setColorFilter(Color.rgb(255, 165, 2))
                        movieBad_Button.setColorFilter(Color.rgb(47, 53, 66))
                        likeyn = "Y"
                    }
                    else -> {
                        // 어떠한 버튼이 눌려있지 않은 상태에서 좋아요버튼 누를 때
                        goodCountTemp += 1
                        isGoodButtonClicked = true
                        movieGood_Button.setColorFilter(Color.rgb(255, 165, 2))
                        likeyn = "Y"
                    }
                }

                // API 서버에 보낼 goodButtonRequest 작성
                var goodButtonRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        Log.d("like Result", it.toString())
                        movieGoodCount_textView.text = goodCountTemp.toString()
                    },
                    Response.ErrorListener {
                        Log.d("like Result", it.toString())
                    }
                ) {
                    //request에 들어갈 인수들 설정
                    override fun getParams(): MutableMap<String, String> {
                        var params = HashMap<String, String>()

                        params["id"] = movieID.toString()
                        params["likeyn"] = likeyn

                        return params
                    }
                }

                goodButtonRequest.setShouldCache(false)
                AppHelper.requestQueue.add(goodButtonRequest)

            }

            //싫어요 버튼 눌렀을 시
            R.id.movieBad_Button -> {
                var url = "${BASE_URL}${APPLY_LIKE_DISLIKE}"
                var disLikeYN: String
                goodCountTemp = movieGoodCount_textView.text.toString().toInt()
                badCountTemp = movieBadCount_textView.text.toString().toInt()

                when {
                    isGoodButtonClicked -> {
                        goodCountTemp -= 1
                        badCountTemp += 1
                        isGoodButtonClicked = false
                        isBadButtonClicked = true
                        movieGoodCount_textView.text = goodCountTemp.toString()
                        movieGood_Button.setColorFilter(Color.rgb(47, 53, 66))
                        movieBad_Button.setColorFilter(Color.rgb(255, 165, 2))
                        disLikeYN = "Y"
                    }
                    isBadButtonClicked -> {
                        badCountTemp -= 1
                        isBadButtonClicked = false
                        movieBad_Button.setColorFilter(Color.rgb(47, 53, 66))
                        disLikeYN = "N"
                    }
                    else -> {
                        badCountTemp += 1
                        isBadButtonClicked = true
                        movieBad_Button.setColorFilter(Color.rgb(255, 165, 2))
                        disLikeYN = "Y"
                    }
                }

                var badButtonRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        Log.d("dislike Result", it.toString())
                        movieBadCount_textView.text = badCountTemp.toString()
                    },
                    Response.ErrorListener {
                        Log.d("dislike Result", it.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        var params = HashMap<String, String>()

                        params["id"] = movieID.toString()
                        params["dislikeyn"] = disLikeYN

                        return params
                    }
                }

                badButtonRequest.setShouldCache(false)
                AppHelper.requestQueue.add(badButtonRequest)

            }

        }

    }

}
