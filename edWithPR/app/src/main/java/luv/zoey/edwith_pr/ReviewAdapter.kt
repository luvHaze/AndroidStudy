package luv.zoey.edwith_pr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// RecyclerView 사용법 3. 리싸이클러 어뎁터를 만들어 준다.
class ReviewAdapter(private var items: MutableList<ReviewItem>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    // 뷰 홀더란 ?  - 화면에 표시할 아이템뷰를 저장하는 공간
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var userReview: TextView
        var ratingBar: RatingBar

        init {
            userReview = v.findViewById(R.id.contentReview_textView)
            ratingBar = v.findViewById(R.id.userRating_ratingBar)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view.
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_review_item, parent, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 뷰 홀더에 만든 아이템 뷰와 데이터를 바인딩 하는 부분
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        var item: ReviewItem = items.get(position)

        holder.userReview.setText(item.content)
        holder.ratingBar.setRating(item.rating)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    fun reutrnRating(): Float {

        var sumRating: Float = 0F

        items.forEach { position ->
            sumRating += position.rating
        }

        return sumRating/items.size
    }

}