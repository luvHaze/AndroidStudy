package luv.zoey.edwith_pr.Review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_review_read.*
import luv.zoey.edwith_pr.R

class ReviewReadActivity : AppCompatActivity() {


    var dataList: ArrayList<ReviewItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_read)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Main에서 받은 인텐트에서 데이터를 추출해 내고
        dataList= intent.extras?.getParcelableArrayList<ReviewItem>("content") as ArrayList<ReviewItem>


        //어뎁터 설정을 한 뒤 내보내 준다.
        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        readReviewAll_RecyclerView.layoutManager=layoutManager

        // 어뎁터 설정
        readReviewAll_RecyclerView.adapter=
            ReviewAdapter(dataList)
    }
}
