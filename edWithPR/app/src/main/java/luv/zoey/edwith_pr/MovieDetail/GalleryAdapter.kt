package luv.zoey.edwith_pr.MovieDetail

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.gallery_item.view.*
import luv.zoey.edwith_pr.MovieDetail.Data.MovieDetailDTO
import luv.zoey.edwith_pr.R
import java.util.zip.Inflater

class GalleryAdapter(private var item: ArrayList<String>, private var item2 :ArrayList<String>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {


    lateinit var itemContext : Context
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var thumbnail_imageView: ImageView = v.findViewById(R.id.thumbnail_imageView)
        var enabledPlay_imageView: ImageView = v.findViewById(R.id.enabledPlay_imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)

        itemContext=parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView).load(item).into(holder.thumbnail_imageView)


        //holder.enabledPlay_imageView.visibility = ImageView.VISIBLE
    }


    override fun getItemCount(): Int = item!!.size




}