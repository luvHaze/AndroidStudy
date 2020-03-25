package com.example.dimoproject

import android.content.Context
import android.util.AttributeSet
import kotlinx.android.synthetic.main.view_info.view.*
import java.text.SimpleDateFormat
import java.util.*

// InfoView를 상속받아
// 알람전용 View로 만드는 과정


class  AlarmInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    :InfoView(context,attrs,defStyleAttr){
    companion object{
        //알람의 시간 표시형식 만들어 줌
        private val dateFormat = SimpleDateFormat("yy/MM/dd HH:mm")
    }

    // View에 표시할 초기값을 지정해줌
    init {
        typeImage.setImageResource(R.drawable.ic_alarm)
        infoText.setText("")
    }

    //외부에서 시간을 입력받고
    fun setAlarmDate(alarmDate: Date){
        //알람시간이 현재 시간보다 이전이면 알람 없다고 표시
        if(alarmDate.before(Date())){
            infoText.setText("알람이 없습니다.")
        }
        else{
            infoText.setText(dateFormat.format(alarmDate))
        }
    }
}