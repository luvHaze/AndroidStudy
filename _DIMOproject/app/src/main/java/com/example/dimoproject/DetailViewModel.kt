package com.example.dimoproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dimoproject.Data.MemoDAO
import com.example.dimoproject.Data.MemoData
import io.realm.Realm


// DetailActivity에서 사용하는 View Model 이다.
class DetailViewModel : ViewModel() {
    //제목과 내용에 로드할 내용을
    //MutableLiveData로 선언
    val title: MutableLiveData<String> = MutableLiveData<String>().apply { value=""}
    val content: MutableLiveData<String> = MutableLiveData<String>().apply {value="" }

    private var memoData = MemoData()   // MemoData를 저장할 때 사용할 변수를 선언해 둠

    private val realm:Realm by lazy {
        Realm.getDefaultInstance()
    }
    private  val memoDao : MemoDAO by lazy {
        MemoDAO(realm)
    }
    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

    fun loadMemo(id:String){
        memoData = memoDao.selectMemo(id)
        title.value = memoData.title
        content.value=memoData.content
    }

    fun addOrUpdateMemo(title:String,content :String){
        memoDao.addOrUpdateMemo(memoData,title,content)
    }

}