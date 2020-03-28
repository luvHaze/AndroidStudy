package com.example.dimoproject.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm

class ListViewModel : ViewModel(){
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    //Realm 인스턴스를 넣어 Memo Dao를 생성하여 사용하는 변수
    private val memoDao : MemoDAO by lazy {
        MemoDAO(realm)
    }

    // MemoDAO 에서 모든 메모를 가져와서 RealmLiveData로 변환하여 사용하는 변수
    val memoLiveData : RealmLiveData<MemoData> by lazy {
        RealmLiveData<MemoData> (memoDao.getAllMemos())
    }

    //LiveViewModel을 더이상 사용하지 않을때 Realm 인스턴스를 닫아준다.
    override fun onCleared() {
        super.onCleared()
        realm.close()
    }
}