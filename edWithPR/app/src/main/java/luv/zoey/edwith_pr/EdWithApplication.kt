package luv.zoey.edwith_pr

import android.app.Application
import io.realm.Realm

class EdWithApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(applicationContext)
    }

}