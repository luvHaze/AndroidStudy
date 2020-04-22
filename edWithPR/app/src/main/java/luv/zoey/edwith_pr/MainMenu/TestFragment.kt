package luv.zoey.edwith_pr.MainMenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test.*

import luv.zoey.edwith_pr.R
import luv.zoey.edwith_pr.Review.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class TestFragment(private var movie: Movie) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDetail_TextView.setOnClickListener {
            var intent = Intent(view.context, MainActivity::class.java)
            startActivity(intent)
        }

        menuFragment_movieTitle.text = movie.title
        menuFragment_GradeRate.text =
            "${movie.grade}세 이용가 | 예매율 : ${movie.reservation_rate}% "

        Log.d("data",movie.toString())
    }
}
