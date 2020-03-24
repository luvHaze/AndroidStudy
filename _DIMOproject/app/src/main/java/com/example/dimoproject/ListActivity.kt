package com.example.dimoproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dimoproject.Data.ListViewModel
import com.example.dimoproject.Data.MemoData
import com.example.dimoproject.Data.MemoListFragment

import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

class ListActivity : AppCompatActivity() {

    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(applicationContext, DetailAcitivity::class.java)
            startActivity(intent)

        }

        // MemoListFragment를 화면에 표시 (중요)
        val fragmentTransation = supportFragmentManager.beginTransaction()
        fragmentTransation.replace(
            R.id.contentlayout,
            MemoListFragment()
        )       // contentlayout 에 MemoListFragment를 표시한다.
        fragmentTransation.commit()

        //ListViewModel 을 가져오는 코드
        viewModel = application!!.let {
            //ViewModel을 가져오기 위해 ViewModelProvider 객체생성
            ViewModelProvider(
                viewModelStore,
                ViewModelProvider.AndroidViewModelFactory(it)
            ) // viewModelStore -> ViewModel의 생성과 소멸의 기준
                .get(ListViewModel::class.java)                                             // AndroidViewModelFactory - > ViewModel을 실제로 생성하는 객체
            // get 함수 사용으로 객체 생성
        }


    }

}
