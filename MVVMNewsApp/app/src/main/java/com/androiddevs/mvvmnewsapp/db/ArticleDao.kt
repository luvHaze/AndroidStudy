package com.androiddevs.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androiddevs.mvvmnewsapp.models.Article

@Dao
interface ArticleDao {

    // onConfilict -> 삽입시 중복되는 경우에는 지금 삽입하는값으로 대체
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Delete
    suspend fun deleteArticle(article: Article)

    //getallArticles는 로컬 데이터베이스 조회이기 때문에 비동기 처리를 해주지 않는다.
    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>


}