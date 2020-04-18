package luv.zoey.edwith_pr.MainMenu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.action_bar.view.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_menu.*
import luv.zoey.edwith_pr.R
import kotlin.math.log

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    var fragList: MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val action_bar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.menu_toolbar)
        setSupportActionBar(findViewById(R.id.menu_toolbar)) // 액션바 등록

//        activity_menu 레이아웃은 드로어 레이아웃 메인
//        child 로는 툴바가 들어갈 LinearLayout과
//        액션바를 눌렀을때 띄워질 네비게이션 뷰가 있다.
//        Drawer 레이아웃은 같은 그래비티 끼리 열어줄 수 있다

        //네비게이션 리스너 등록해준다
        navigation_menu.setNavigationItemSelectedListener(this)


        openNavigationView_Button.setOnClickListener {
            // 그래비티가 LEFT에 해당하는 뷰들을 '서랍 열듯이 열겠다' 라는 의미
            drawer.openDrawer(Gravity.LEFT)

        }

//        val viewPagerAdapter = MenuViewPagerAdapter(supportFragmentManager).apply {
//            addItem(TestFragment())
//            addItem(TestFragment2())
//        }
//
//        menu_viewPager.adapter = viewPagerAdapter


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.movieList_item -> {

                Toast.makeText(this, "sdf", Toast.LENGTH_LONG).show()
            }

            R.id.movieAPI_item -> {

            }
            R.id.movieReserve_item -> {

            }


        }
        return false
    }

}
