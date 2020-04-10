package luv.zoey.edwith_pr

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        // 어뎁터로 보낼 데이터
        var dataSet : MutableList<String> = arrayListOf("가나다","마바사","사가라","안철수")



        // RecyclerView 사용법 4. 리싸이클러 뷰 방향설정
        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        readMovieReview_recyclerView.setLayoutManager(layoutManager)

        // RecyclerView 사용법 4. 리싸이클러 뷰에 어뎁터 설정해주기 끝.
        readMovieReview_recyclerView.adapter=ReviewAdapter(dataSet)

        writeMovieReview_button.setOnClickListener {

            val view = LayoutInflater.from(this).inflate(R.layout.alert_write_review,null)

            AlertDialog.Builder(this)
                .setView(view)
                .setTitle(movieName_textview.text.toString())
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()

                })
                .setPositiveButton("작성", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
              .show()


        }

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

            if(isGoodButtonClicked){
                movieGood_Button.setColorFilter(Color.rgb(255,165,2))
                movieBad_Button.setColorFilter(Color.rgb(47,53,66))
            }else{
                movieGood_Button.setColorFilter(Color.rgb(47,53,66))
            }
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

            if(isBadButtonClicked){
                movieBad_Button.setColorFilter(Color.rgb(255,165,2))
                movieGood_Button.setColorFilter(Color.rgb(47,53,66))
            }else{
                movieBad_Button.setColorFilter(Color.rgb(47,53,66))
            }
        }



    }
}
