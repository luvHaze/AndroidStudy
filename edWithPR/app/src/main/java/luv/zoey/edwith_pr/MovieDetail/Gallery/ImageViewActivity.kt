package luv.zoey.edwith_pr.MovieDetail.Gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import luv.zoey.edwith_pr.R

class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        Toast.makeText(this,intent.extras?.getString("URL").toString(),Toast.LENGTH_LONG).show()

    }
}
