package com.example.exam_instagram.navigation

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exam_instagram.R
import com.example.exam_instagram.navigation.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailViewFragment : Fragment() {

    var firestore: FirebaseFirestore? = null
    var uid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_detail, container, false)
        firestore = FirebaseFirestore.getInstance()

        view.detailviewfragment_recyclerview.adapter = DetailViewRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager = LinearLayoutManager(activity)
        return view
    }

    inner class DetailViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var contentDTOs: ArrayList<ContentDTO> = arrayListOf()
        var contentUidList: ArrayList<String> = arrayListOf()

        init {


            firestore?.collection("images")?.orderBy("timestamp")
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                    contentDTOs.clear()
                    contentUidList.clear()
                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(ContentDTO::class.java)
                        contentDTOs.add(item!!)
                        contentUidList.add(snapshot.id)
                    }
                    notifyDataSetChanged()

                }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun getItemCount(): Int {
            return contentDTOs.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder = (holder as CustomViewHolder).itemView

            //UserId
            viewholder.detailviewitem_profile_textview.text = contentDTOs!![position].userID

            //Image
            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUrl)
                .into(viewholder.detailviewitem_imageview_content)

            //Explain of content
            viewholder.detailviewitem_explain_textview.text = contentDTOs!![position].explain

            // likes
            viewholder.detailviewitem_favoritecounter_textview.text =
                "Likes" + contentDTOs!![position].favortiteCount

            //profileImage
//            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUrl).into(viewholder.detailviewitem_profile_image)

            //하트 버튼이 클릭됐을 경우
            viewholder.detailviewitem_favorite_imgview.setOnClickListener {
                favoriteEvent(position)

            }

            //좋아요 카운트하고 하트가 변경
            if (contentDTOs!![position].chkfavorites.containsKey(FirebaseAuth.getInstance().currentUser!!.uid)) {
                //좋아요 버튼 클릭된 경우
                viewholder.detailviewitem_favorite_imgview.setImageResource(R.drawable.ic_favorite)
            } else {
                //좋아요 클릭안된경우
                viewholder.detailviewitem_favorite_imgview.setImageResource(R.drawable.ic_favorite_border)
            }

            viewholder.detailviewitem_profile_image.setOnClickListener(){
                var fragment = UserFragment()
                var bundle = Bundle()
                bundle.putString("destinationUid",contentDTOs[position].uid)
                bundle.putString("userId",contentDTOs[position].userID)
                fragment.arguments=bundle
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContent,fragment)?.commit()
            }


        }

        fun favoriteEvent(position: Int) {
            var tsDoc = firestore?.collection("images")?.document(contentUidList[position])
            firestore?.runTransaction { transaction ->

                var uid = FirebaseAuth.getInstance().currentUser?.uid
                var contentDTO = transaction.get(tsDoc!!).toObject(ContentDTO::class.java)

                if (contentDTO!!.chkfavorites.containsKey(uid)) {
                    //when the button is clicked
                    contentDTO?.favortiteCount = contentDTO?.favortiteCount!! - 1
                    contentDTO.chkfavorites.remove(uid)

                } else {

                    contentDTO?.favortiteCount = contentDTO?.favortiteCount!! + 1
                    contentDTO?.chkfavorites[uid!!] = true
                }

                transaction.set(tsDoc, contentDTO)

            }

        }

    }
}