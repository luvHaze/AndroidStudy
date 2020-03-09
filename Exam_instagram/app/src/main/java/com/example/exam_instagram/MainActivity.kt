package com.example.exam_instagram

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.exam_instagram.navigation.AlarmFragment
import com.example.exam_instagram.navigation.DetailViewFragment
import com.example.exam_instagram.navigation.GridFragment
import com.example.exam_instagram.navigation.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        setToolbarDefault()
        when (p0.itemId) {
            R.id.action_home -> {
                var detailViewFragment = DetailViewFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContent, detailViewFragment).commit()
                return true
            }

            R.id.action_search -> {
                var gridFragment = GridFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContent, gridFragment).commit()
                return true
            }
            R.id.add_photo -> {

                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                    startActivity(Intent(this,AddPhotoActivity::class.java))
                }

                return true
            }
            R.id.action_fav -> {
                var alarmFragment = AlarmFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContent, alarmFragment).commit()
                return true
            }
            R.id.action_account -> {
                var userFragment = UserFragment()
                var bundle=Bundle()
                var uid = FirebaseAuth.getInstance().currentUser?.uid
                bundle.putString("destinationUid",uid)
                userFragment.arguments=bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContent, userFragment).commit()
                return true
            }
        }
        return false
    }

    fun setToolbarDefault(){
        toolbar_username.visibility= View.GONE
        btn_toolbar_back.visibility=View.GONE
        toolbar_title_image.visibility=View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)

        ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)

        //Set default Screen
        bottom_navigation.selectedItemId=R.id.action_home
    }


}
