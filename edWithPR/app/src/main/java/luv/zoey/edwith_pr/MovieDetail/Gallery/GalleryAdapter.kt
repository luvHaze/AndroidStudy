package luv.zoey.edwith_pr.MovieDetail.Gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.gallery_item.view.*
import luv.zoey.edwith_pr.MovieDetail.Data.MovieDetailDTO
import luv.zoey.edwith_pr.R

class GalleryAdapter(private var item: MovieDetailDTO) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var videoList = item.videos!!.split(',') as ArrayList<String>
    var imageList = item.photos!!.split(',') + videoList

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var thumbnail_imageView: ImageView = v.findViewById(R.id.thumbnail_imageView)
        var enabledPlay_imageView: ImageView = v.findViewById(R.id.enabledPlay_imageView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)

        view.setOnClickListener {
            if(view.enabledPlay_imageView.visibility==View.VISIBLE){
                Toast.makeText(parent.context,"동영상",Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(parent.context,"사진",Toast.LENGTH_LONG).show()
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(imageList[position].contains("youtu")){
            holder.enabledPlay_imageView.visibility = ImageView.VISIBLE
            val id = imageList[position].substring(imageList[position].lastIndexOf("/") + 1)
            val result = "https://img.youtube.com/vi/$id/default.jpg"
            Glide.with(holder.itemView).load(result).into(holder.thumbnail_imageView)
        }else{
            Glide.with(holder.itemView).load(imageList[position]).into(holder.thumbnail_imageView)
        }
        Log.d("ReCyclerView position", position.toString())


    }

    override fun getItemCount(): Int = imageList.size


}