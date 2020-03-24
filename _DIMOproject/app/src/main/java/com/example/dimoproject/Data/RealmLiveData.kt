package com.example.dimoproject.Data

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmObject
import io.realm.RealmResults

class RealmLiveData<T : RealmObject>(private val realmResults: RealmResults<T>) :
    LiveData<RealmResults<T>>() {

    //받아온 realmResult를 value에 추가
    //(Observe 가 동작하도록 하기 위해)
    init {
        value = realmResults
    }

    //RealmResult가 갱신될때 동작할 리스너 작성
    //(갱신되는 값을 value에 할당
    private val listener =
        RealmChangeListener<RealmResults<T>>{value = it}

    //LiveData가 활성화 될 때 realmResults에 리스너를 붙여줌
    override fun onActive() {
        super.onActive()
        realmResults.addChangeListener (listener)
    }

    //LiveData가 비활성화 될땐 삭제
    override fun onInactive() {
        super.onInactive()
        realmResults.removeChangeListener (listener)
    }
}