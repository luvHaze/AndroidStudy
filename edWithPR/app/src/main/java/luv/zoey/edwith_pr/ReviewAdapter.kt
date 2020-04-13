package luv.zoey.edwith_pr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// RecyclerView 사용법 3. 리싸이클러 어뎁터를 만들어 준다.
class ReviewAdapter() : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    var items : ArrayList<ReviewItem> = arrayListOf()

    // 뷰 홀더란 ?  - 화면에 표시할 아이템뷰를 저장하는 공간
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var userName: TextView
        var userReview: TextView
        init {
            userName = v.findViewById<TextView>(R.id.userName_textView)
            userReview = v.findViewById(R.id.contentReview_textView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view.
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_review_item, parent, false)

        return ViewHolder(item)
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 뷰 홀더에 만든 아이템 뷰와 데이터를 바인딩 하는 부분
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        holder.userName.text = items.get(position).name
        holder.userReview.text = items.get(position).content
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    fun addItem(item : ReviewItem){
        items.add(item)
    }

}