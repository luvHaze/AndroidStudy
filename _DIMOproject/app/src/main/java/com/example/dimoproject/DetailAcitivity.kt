package com.example.dimoproject

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_detail_acitivity.*
import kotlinx.android.synthetic.main.content_detail_acitivity.*


class DetailAcitivity : AppCompatActivity() {

    private var viewModel: DetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_acitivity)
        setSupportActionBar(infoLayout)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //viewModel 의 생성
        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(DetailViewModel::class.java)
        }

        // 제목과 내용에 Observer를 걸어 화면을 갱신시킨다.
        viewModel!!.let {
            it.title.observe(this, Observer { supportActionBar?.title = it })
            it.content.observe(this, Observer { contentEdit.setText(it) })
        }

        //ListActivity에서 아이템을 선택했을때 보내주는 메모 id로 데이터를 로드함
        // 새로 작성하는 메모의 경우  메모 id가 없어 이 루틴은 동작하지 않는다.
        val memoId = intent.getStringExtra("MEMO_ID")
        if (memoId != null) viewModel!!.loadMemo(memoId)

        // 툴바 레이아웃을 눌렀을때 제목을 수정하는 다이얼로그를 띄우는 루틴
        toolbar_layout.setOnClickListener {
            //LayoutInflater로 레이아웃 xml을 view로 변환
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_title, null)
            val titleEdit = view.findViewById<EditText>(R.id.titleEdit)
            // View의 findViewById 함수를 이용하여 view에 포함된 titleedit을 변수에 담음

            //AlertDialog.Builder로 다이얼로그 창을 제작
            AlertDialog.Builder(this)
                .setTitle("제목을 입력하세요")
                .setView(view)      //다이얼 로그의 내용이 되는 view 설정
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    supportActionBar?.title = titleEdit.text.toString()
                    toolbar_layout.title=titleEdit.text.toString()
                }).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        // 화면에서 뒤로가기를 누를때
        //viewModel의 addOrUpdateMemo()를 호출하여 메모를 DB에 갱신
        viewModel?.addOrUpdateMemo(
            supportActionBar?.title.toString(),
            contentEdit.text.toString()
        )
    }
}
