package luv.zoey.edwith_pr

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isGoodButtonClicked = false
        var isBadButtonClicked = false
        var dataSet : MutableList<String> = arrayListOf("가나다","마바사","사가라")

        //리싸이클러 뷰 방향설정
        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        readMovieReview_recyclerView.setLayoutManager(layoutManager)

        readMovieReview_recyclerView.adapter=ReviewAdapter(dataSet)

        movieGood_Button.setOnClickListener {
            var goodCountTemp = movieGoodCount_textView.text.toString().toInt()
            var badCountTemp = movieBadCount_textView.text.toString().toInt()

            if (isGoodButtonClicked) {
                goodCountTemp -= 1
                isGoodButtonClicked = false
            }
            else if (isBadButtonClicked) {
                badCountTemp -= 1
                goodCountTemp += 1
                movieBadCount_textView.text = badCountTemp.toString()
                isGoodButtonClicked = true
                isBadButtonClicked = false
            }
            else {
                goodCountTemp += 1
                isGoodButtonClicked = true
                movieGood_Button.setColorFilter(Color.rgb(155,155,0))
            }

            movieGoodCount_textView.setText(goodCountTemp.toString())
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
            }
            else if (isBadButtonClicked) {
                badCountTemp -= 1
                isBadButtonClicked = false
            }
            else {
                badCountTemp += 1
                isBadButtonClicked = true
            }

            movieBadCount_textView.setText(badCountTemp.toString())
        }



    }
}
