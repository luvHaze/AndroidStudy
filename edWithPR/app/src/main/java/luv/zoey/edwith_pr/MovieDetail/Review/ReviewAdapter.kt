package luv.zoey.edwith_pr.MovieDetail.Review

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import luv.zoey.edwith_pr.AppHelper
import luv.zoey.edwith_pr.MovieDetail.ReviewData.MovieReviewDTO
import luv.zoey.edwith_pr.R

// RecyclerView 사용법 3. 리싸이클러 어뎁터를 만들어 준다.
class ReviewAdapter(private var items: ArrayList<MovieReviewDTO>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    // 뷰 홀더란 ?  - 화면에 표시할 아이템뷰를 저장하는 공간
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var contents: TextView = v.findViewById(R.id.contentReview_textView)
        var ratingBar: RatingBar = v.findViewById(R.id.userRating_ratingBar)
        var writeTime: TextView = v.findViewById(R.id.writeTime_textView)
        var userName: TextView = v.findViewById(R.id.userName_textView)
        var recommand: TextView = v.findViewById(R.id.recommandCount_textView)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view.
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_review_item, parent, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    // * 뷰 홀더에 만든 아이템 뷰와 데이터를 바인딩 하는 부분
    // * 각 아이템에 대한 클릭 리스너도 여기에 처리를 해준다.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        var item: MovieReviewDTO = items[position]

        holder.apply {
            contents.text = item.contents
            ratingBar.rating = item.rating!!
            writeTime.text = item.time
            userName.text = item.writer
            recommand.text = "추천 ${item.recommend}"
        }

        holder.recommand.setOnClickListener {

            val recommandRequestURL = "http://boostcourse-appapi.connect.or.kr:10000/movie/increaseRecommend"

            var recommandRequest = object : StringRequest(
                Request.Method.POST,
                recommandRequestURL,
                Response.Listener{
                    Log.d("Recommend Success", it)
                },
                Response.ErrorListener {
                    Log.d("Recommend Error", it.toString())
                }
            ){
                override fun getParams(): HashMap<String, String?> {
                    val params = HashMap<String,String?>()

                    params["review_id"]=item.id.toString()
                    params["writer"]=item.writer

                    return params
                }
            }

            recommandRequest.setShouldCache(false)
            AppHelper.requestQueue.add(recommandRequest)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    // 모든 리뷰들을 평균내주는 메소드
    fun reutrnRating(): Float {

        var sumRating: Float = 0F

        items.forEach { position ->
            sumRating += position.rating!!
        }

        return sumRating / items.size
    }


}