package com.example.dimoproject

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimoproject.Data.MemoDAO
import com.example.dimoproject.Data.MemoData
import com.example.dimoproject.Data.WeatherData
import io.realm.Realm
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
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

    fun deleteWeather(){
        memoData.weather=""
        memoLiveData.value=memoData
    }

    fun setWeather(latitude: Double,longitude: Double){
        //받아온 좌표를 WeatherData에 넘겨 날씨를 가져와 memoData에 저장하는 함수
        //viewModelScope는 ViewModel이 소멸할때에 맞춰 코루틴을 정지시켜줌
        viewModelScope.launch {
            memoData.weather=WeatherData.getCurrentWeather(latitude,longitude)
            memoLiveData.value=memoData
        }

    }

    fun setImageFile(context: Context,bitmap: Bitmap){
        val imageFile = File(
            // 앱 데이터 폴더 내에 image라는 하위 폴더 지정
            context.getDir("image",Context.MODE_PRIVATE)
            ,memoData.id+".jpg"
        )

        if(imageFile.exists()) imageFile.delete()

        try {
            imageFile.createNewFile()
            //FileOutputStream으로 패러미터로 받은 이미지 테이터를
            //JPEG으로 압축하여 저장하고 stream 객체로 닫아줌
            val outputStream=FileOutputStream(imageFile)

            //저장이 끝나면 저장한 이미지 이름을 memo Data에 갱신함
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
            outputStream.close()

            memoData.imageFile=memoData.id+".jpg"
            memoLiveData.value=memoData
        }
        catch (e:Exception){
            println(e)
        }
    }

}