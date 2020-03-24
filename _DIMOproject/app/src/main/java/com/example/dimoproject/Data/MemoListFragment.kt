package com.example.dimoproject.Data

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimoproject.DetailAcitivity
import com.example.dimoproject.R
import kotlinx.android.synthetic.main.fragment_memo_list.*

/**
 * A simple [Fragment] subclass.
 */
class MemoListFragment : Fragment() {

    private lateinit var listAdapter: MemoListAdapter
    private var viewModel: ListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity!!.application!!.let {
            ViewModelProvider(
                activity!!.viewModelStore,                          // activity를 붙여주는 이유는 Fragment와 데이터를 공유하기 위함
                ViewModelProvider.AndroidViewModelFactory(it)
            ).get(ListViewModel::class.java)

        }

        viewModel!!.let {
            it.memoLiveData.value?.let {
                //MemoLiveData를 가져와서 Adapter에 담아 RecyclerView에 출력하도록 함
                listAdapter = MemoListAdapter(it)
                memoListView.layoutManager =
                    LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                memoListView.adapter = listAdapter

                //ListAdapter의 itemClickListener에서 DetailActivity로 이동하도록 함 + 메모id도 전달함
                listAdapter.itemClickListener = {
                    val intent = Intent(activity, DetailAcitivity::class.java)
                    intent.putExtra("MEMO_ID", it)
                    startActivity(intent)
                }
            }
            //MemoLiveData에 observe 함수를 통해 값이 변할 때 동작할 observer를 붙여준다.
            // (Observer 내에서는 adapter의 갱신코드를 호출
            it.memoLiveData.observe(viewLifecycleOwner, Observer {
                listAdapter.notifyDataSetChanged()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        // 메모를 작성하고 되돌아 왔을때
        // 리스트가 갱신되도록 함
        listAdapter.notifyDataSetChanged()

    }
}
