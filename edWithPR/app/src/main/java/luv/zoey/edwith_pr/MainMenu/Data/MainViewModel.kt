package luv.zoey.edwith_pr.MainMenu.Data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "database-name"
    ).build()

    fun insert(movie: Movie) {
        db.movieDao().insert(movie)
    }

    fun getAll(): ArrayList<Movie> {
        return db.movieDao().getAll()
    }


}