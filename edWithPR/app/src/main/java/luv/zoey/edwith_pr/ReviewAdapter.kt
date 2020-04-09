package luv.zoey.edwith_pr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val dataSet: MutableList<String>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        var userName : TextView

        init {
            userName = v.findViewById<TextView>(R.id.userName_textView)
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
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        holder.userName.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}