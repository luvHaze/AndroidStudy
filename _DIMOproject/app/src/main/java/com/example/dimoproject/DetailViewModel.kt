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

//    //제목과 내용에 로드할 내용을 MutableLiveData로 선언
////    val title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
////    val content: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
////    val alarmTime: MutableLiveData<Date> = MutableLiveData<Date>().apply { value = Date(0) }
////
////    private var memoData = MemoData()   // MemoData를 저장할 때 사용할 변수를 선언해 둠

    // 새 memo Data변수 및 memoLiveData 변수 추가
    var memoData = MemoData()
    val memoLiveData: MutableLiveData<MemoData> by lazy {
        MutableLiveData<MemoData>().apply { value = memoData }
    }

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
        // 기존에는 DB에서 가져온 메모를 managed 상태에서 사용했지만
//        memoData = memoDao.selectMemo(id)
//        title.value = memoData.title
//        content.value = memoData.content
//        alarmTime.value = memoData.alarmTime

        // copyFromRealm()으로 unmanaged 객체로 만들어 직접사용해서 원하는 시점에 DB에 갱신할 수 있도록 한다.
        memoData = realm.copyFromRealm(memoDao.selectMemo(id))
        memoLiveData.value = memoData

    }

    fun deleteAlarm() {
//        alarmTime.value = Date(0)
        memoData.alarmTime = Date(0)
        memoLiveData.value = memoData
    }

    fun setAlarm(time: Date) {
//        alarmTime.value = time
        memoData.alarmTime = time
        memoLiveData.value = memoData
    }

    fun deleteLocation() {
        memoData.latitude = 0.0
        memoData.longitude = 0.0
        memoLiveData.value = memoData
    }

    fun setLocation(latitude: Double, longitude: Double) {
        memoData.latitude = latitude
        memoData.longitude = longitude
        memoLiveData.value = memoData
    }

    fun addOrUpdateMemo(context: Context) {

        memoDao.addOrUpdateMemo(memoData)

        // AlarmTool을 통해 메모와 연경된 기존 알람정보를 삭제하고
        // 새 알람 시간이 현재시간 이후라면 새로 등록해 줌
        AlarmTool.deleteAlarm(context, memoData.id)
        if (memoData.alarmTime.after(Date())) {
            AlarmTool.addAlarm(context, memoData.id, memoData.alarmTime)
        }
    }

}