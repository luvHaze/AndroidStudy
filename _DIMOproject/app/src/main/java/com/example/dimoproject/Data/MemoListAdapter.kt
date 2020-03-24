package com.example.dimoproject.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dimoproject.R
import kotlinx.android.synthetic.main.item_memo.view.*
import java.text.SimpleDateFormat


class MemoListAdapter(private val list: MutableList<MemoData>) :
    RecyclerView.Adapter<ItemViewHolder>() {

    private val dateFormat = SimpleDateFormat("MM/dd HH:mm")
    lateinit var itemClickListener: (itemID: String) -> Unit

    //item_memo 를 불러 ViewHolder를 생성함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)

        //아이템이 클릭될 때 view의 tag에서
        //메모id를 받아서 리스너에 넘김(tag는 아래서 추가)
        view.setOnClickListener {

            itemClickListener?.run {
                val memoId = it.tag as String
                this(memoId)
            }
        }
        return ItemViewHolder(view)

    }

    // List에 담긴 메모의 수 반환
    override fun getItemCount(): Int {

        return list.count()
    }

    //제목부터 표시
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (list[position].title.isNotEmpty()) {
            holder.containerView?.titleView?.visibility =
                View.VISIBLE                                                 //visiviliy - 1. VISIBLE   View를 화면에 표시
            holder.containerView?.titleView?.text =
                list[position].title                                          //            2. INVISIBLE View 내용만 감추고 영역은 유지
        } else {                                                              //            3. GONE       내용 및 영역까지 제거
            holder.containerView?.titleView?.visibility = View.GONE
        }
        holder.containerView?.summaryView?.text = list[position].summary
        holder.containerView?.dateView?.text = dateFormat.format(list[position].createdAt)
        holder.containerView?.tag=list[position].id
        //아이템 view에 메모의 id를 설정해 줌
    }

}