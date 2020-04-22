package luv.zoey.edwith_pr.MainMenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie.*
import luv.zoey.edwith_pr.MainMenu.Data.Movie

import luv.zoey.edwith_pr.R
import luv.zoey.edwith_pr.Review.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment(private var movie: Movie) : Fragment() {

    lateinit var imgLoadTask : ImageLoadTask

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
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

        /*     # AsyncTask 를 이용해서 이미지를 가져오는 법
        imgLoadTask=ImageLoadTask(movie.image,menuFragment_moviePicture)
        imgLoadTask.execute()
        */

        // Glide를 이용해서 이미지를 가져오는 법
        Glide.with(view.context).load(movie.image).into(menuFragment_moviePicture)
        menuFragment_movieTitle.text = movie.title
        menuFragment_GradeRate.text =
            "${movie.grade}세 이용가 | 예매율 : ${movie.reservation_rate}% "
    }

    override fun onStart() {
        super.onStart()

    }
}
