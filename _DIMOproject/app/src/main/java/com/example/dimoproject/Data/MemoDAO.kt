package com.example.dimoproject.Data

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.util.*

class MemoDAO(private val realm: Realm) {
    fun getAllMemos(): RealmResults<MemoData> {
        return realm.where(MemoData::class.java)
            .sort("createdAt", Sort.DESCENDING)
            .findAll()
    }

    // 지정된 id의 메모를 가져와서 반환하는 함수
    fun selectMemo(id: String): MemoData {
        return realm.where(MemoData::class.java)
            .equalTo("id", id)
            .findFirst() as MemoData
    }

    fun addOrUpdateMemo(memoData: MemoData, title: String, content: String) {
        //execute Transaction() 으로 감싸면 쿼리가 끝날때까지 DB 를 안전하게 사용가능
        //(DB 업데이트 하는 쿼리는 반드시 감싸줘야 간섭에 안전)
        realm.executeTransaction {
            memoData.title = title
            memoData.content = content
            memoData.createdAt = Date()

            if (content.length > 100)
                memoData.summary = content.substring(0..100)
            else
                memoData.summary = content


            //Managed 상태가 아닌 경우 copyToRealm() 함수로 DB에 추가
            if (!memoData.isManaged) {
                it.copyToRealm(memoData)
            }
        }

    }


}