package luv.zoey.edwith_pr.MainMenu.Data

import androidx.room.Database

@Database(entities = [Movie::class],version = 1)
abstract class AppDatabase {
    abstract fun movieDao() : MovieDao

}