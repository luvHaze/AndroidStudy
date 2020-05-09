package luv.zoey.edwith_pr

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class EdWithApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(applicationContext)

        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }
}