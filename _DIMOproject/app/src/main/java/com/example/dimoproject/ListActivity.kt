package com.example.dimoproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dimoproject.Data.ListViewModel
import com.example.dimoproject.Data.MemoListFragment

import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        // MemoListFragment를 화면에 표시 (중요)
        val fragmentTransation = supportFragmentManager.beginTransaction()
        fragmentTransation.replace(R.id.contentLayout, MemoListFragment())       // contentlayout 에 MemoListFragment를 표시한다.
        fragmentTransation.commit()

        //ListViewModel 을 가져오는 코드
        viewModel = application!!.let {
            //ViewModel을 가져오기 위해 ViewModelProvider 객체생성
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(ListViewModel::class.java) // viewModelStore -> ViewModel의 생성과 소멸의 기준
                   // AndroidViewModelFactory - > ViewModel을 실제로 생성하는 객체
            // get 함수 사용으로 객체 생성
        }
        fab.setOnClickListener { view ->
            val intent = Intent(applicationContext, DetailActivity::class.java)
            startActivity(intent)
        }

    }

}
