package luv.zoey.edwith_pr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_review_read.*

class ReviewReadActivity : AppCompatActivity() {


    var dataList: ArrayList<ReviewItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_read)
        setSupportActionBar(findViewById(R.id.toolbar2))

        actionBar?.setHomeButtonEnabled(true)

        dataList= intent.extras?.getParcelableArrayList<ReviewItem>("content") as ArrayList<ReviewItem>

        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        readReviewAll_RecyclerView.layoutManager=layoutManager

        readReviewAll_RecyclerView.adapter=ReviewAdapter(dataList)
    }
}
