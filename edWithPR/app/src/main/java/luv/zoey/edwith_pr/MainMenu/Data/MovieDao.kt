package luv.zoey.edwith_pr.MainMenu.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Query("SELECT * FROM Movie")
    fun getAll(): ArrayList<Movie>

}