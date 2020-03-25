package com.example.dimoproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.shape.ShapeAppearanceModel.builder
import com.takisoft.datetimepicker.DatePickerDialog
import com.takisoft.datetimepicker.TimePickerDialog
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import java.util.*
import java.util.stream.DoubleStream.builder


class DetailActivity : AppCompatActivity() {

    private var viewModel: DetailViewModel? = null

    //날짜와 시간 다이얼로그에서 설정중인 값을 임시로 저장해 두기 위함
    private val dialogCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

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
            it.alarmTime.observe(this, Observer { alarmInfoView.setAlarmDate(it) })
        }

        //ListActivity에서 아이템을 선택했을때 보내주는 메모 id로 데이터를 로드함
        // 새로 작성하는 메모의 경우  메모 id가 없어 이 루틴은 동작하지 않는다.
        val memoId = intent.getStringExtra("MEMO_ID")
        if (memoId != null) viewModel!!.loadMemo(memoId)

        // 툴바 레이아웃을 눌렀을때 제목을 수정하는 다이얼로그를 띄우는 루틴
        toolbarLayout.setOnClickListener {
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
                    toolbarLayout.title = titleEdit.text.toString()
                }).show()
        }

    }

    private fun openDateDialog() {
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            dialogCalendar.set(year, month, dayOfMonth)
            openTimeDialong()
        }
        datePickerDialog.show()
    }

    private fun openTimeDialong() {
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dialogCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                dialogCalendar.set(Calendar.MINUTE, minute)

                viewModel?.setAlarm(dialogCalendar.time)
                // 시간 다이얼로그는 생성자 안에서 Listner 를 구현
                // 사용자가 입력한 시간을 임시 캘린더 변수에 설정하고
                // 캘린더 변수의 time값(date객체)를 viewModel에 새 알람값으로 설정해 줌
            },
            0, 0, false
        ) // 다디얼로그 초기시간 0시 0분 설정 24시간제 ->false
        timePickerDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_acitivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            //공유기능 아이템 선택 시
            R.id.menu_share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, supportActionBar?.title)
                intent.putExtra(Intent.EXTRA_TEXT, contentEdit.text.toString())

                startActivity(intent)
            }

            R.id.menu_alarm -> {
                if (viewModel?.alarmTime?.value!!.after(Date())) {
                    AlertDialog.Builder(this)
                        .setTitle("안내")
                        .setMessage("기존에 알람이 설정되어 있습니다. 삭제 또는 재설정할 수 있습니다.")
                        .setPositiveButton("재설정", DialogInterface.OnClickListener { dialog, which ->
                            openDateDialog()
                        })
                        .setNegativeButton("삭제", DialogInterface.OnClickListener { dialog, which ->
                            viewModel?.deleteAlarm()
                        })
                        .show()
                } else {
                    openDateDialog()
                }

            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // 화면에서 뒤로가기를 누를때
        //viewModel의 addOrUpdateMemo()를 호출하여 메모를 DB에 갱신
        viewModel?.addOrUpdateMemo(
            this,
            supportActionBar?.title.toString(),
            contentEdit.text.toString()
        )
    }
}