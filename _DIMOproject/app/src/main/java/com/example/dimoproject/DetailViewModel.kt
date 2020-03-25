package com.example.dimoproject

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dimoproject.Data.MemoDAO
import com.example.dimoproject.Data.MemoData
import io.realm.Realm
import java.util.*


// DetailActivity에서 사용하는 View Model 이다.
class DetailViewModel : ViewModel() {
    //제목과 내용에 로드할 내용을 MutableLiveData로 선언
    val title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    val content: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    val alarmTime: MutableLiveData<Date> = MutableLiveData<Date>().apply { value = Date(0) }

    private var memoData = MemoData()   // MemoData를 저장할 때 사용할 변수를 선언해 둠

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    private val memoDao: MemoDAO by lazy {
        MemoDAO(realm)
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

    fun loadMemo(id: String) {
        memoData = memoDao.selectMemo(id)
        title.value = memoData.title
        content.value = memoData.content
        alarmTime.value = memoData.alarmTime
    }

    fun deleteAlarm() {
        alarmTime.value = Date(0)
    }

    fun setAlarm(time: Date) {
        alarmTime.value = time
    }

    fun addOrUpdateMemo(context : Context, title: String, content: String) {
        val alarmtimeValue = alarmTime.value!!
        memoDao.addOrUpdateMemo(memoData, title, content,alarmtimeValue)

        // AlarmTool을 통해 메모와 연경된 기존 알람정보를 삭제하고
        // 새 알람 시간이 현재시간 이후라면 새로 등록해 줌
        AlarmTool.deleteAlarm(context,memoData.id)
        if(alarmtimeValue.after(Date())){
            AlarmTool.addAlarm(context,memoData.id,alarmtimeValue)
        }
    }

}