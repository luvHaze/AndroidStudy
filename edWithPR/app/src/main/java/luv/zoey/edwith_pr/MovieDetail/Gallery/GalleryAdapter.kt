package luv.zoey.edwith_pr.MovieDetail.Gallery

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import luv.zoey.edwith_pr.MovieDetail.Data.MovieDetailDTO
import luv.zoey.edwith_pr.R

class GalleryAdapter(private var item: MovieDetailDTO) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var urlList = item.photos!!.split(',') + item.videos!!.split(',') as ArrayList<String>
    lateinit var url: String

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var thumbnail_imageView: ImageView = v.findViewById(R.id.thumbnail_imageView)
        var enabledPlay_imageView: ImageView = v.findViewById(R.id.enabledPlay_imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (urlList[position].contains("youtu")) {
            holder.enabledPlay_imageView.visibility = ImageView.VISIBLE
            val id = urlList[position].substring(urlList[position].lastIndexOf("/") + 1)
            val result = "https://img.youtube.com/vi/$id/default.jpg"
            Glide.with(holder.itemView).load(result).into(holder.thumbnail_imageView)
        } else {
            Glide.with(holder.itemView).load(urlList[position]).into(holder.thumbnail_imageView)
        }
        Log.d("ReCyclerView position", position.toString())


        holder.itemView.setOnClickListener {

            if (holder.enabledPlay_imageView.visibility == View.VISIBLE) {
                Toast.makeText(holder.itemView.context, "동영상", Toast.LENGTH_LONG).show()
//                val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
//                intent.putExtra("URL", urlList[position])
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data=Uri.parse(urlList[position])
                    `package`=("com.google.android.youtube")
                }

                holder.itemView.context.startActivity(intent)


            } else {
                Toast.makeText(holder.itemView.context, "사진", Toast.LENGTH_LONG).show()
                val intent = Intent(holder.itemView.context, ImageViewActivity::class.java)
                intent.putExtra("URL", urlList[position])
                holder.itemView.context.startActivity(intent)
            }

        }

    }

    override fun getItemCount(): Int = urlList.size


}