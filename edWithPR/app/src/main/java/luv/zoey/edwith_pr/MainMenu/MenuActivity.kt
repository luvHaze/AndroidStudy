package luv.zoey.edwith_pr.MainMenu

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
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

    // API 로 부터 받아온 MovieList들을 담기 위한 배열
    var movieList: ArrayList<MovieListDTO> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.menu_toolbar)) // 액션바 등록

/*      activity_menu 레이아웃은 드로어 레이아웃이 메인레이아웃이며
        child 로는 툴바가 들어갈 LinearLayout과
        액션바를 눌렀을때 띄워질 네비게이션 뷰가 있다.
        Drawer 레이아웃은 같은 그래비티 끼리 열어줄 수 있다*/

        //네비게이션 리스너 등록해준다
        navigation_menu.setNavigationItemSelectedListener(this)

        openNavigationView_Button.setOnClickListener {
            // 그래비티가 LEFT에 해당하는 뷰들을 '서랍 열듯이 열겠다' 라는 의미
            drawer.openDrawer(Gravity.LEFT)

        }

        //뷰페이저 어뎁터 객체를 만들고 addItem으로 프래그먼트를 추가해준다
        val viewPagerAdapter = MenuViewPagerAdapter(supportFragmentManager).apply {

            //TODO 이곳에서 API로 가져온 데이타들을 MovieListDTO타입의 리스트에 저장한 후
            //     for문으로 빼주면서 가져온 개수만큼 프래그먼트 생성해준다.
            movieList.forEach {
                addItem(TestFragment(it))
            }


        }

        //프래그먼트를 추가해준 뒤에는 뷰페이저에 어뎁터를 등록해준다.
        mainmenu_ViewPager.adapter = viewPagerAdapter

    }


    //네비게이션 메뉴 아이템 선택 시 발생하는 콜백메서드 (미구현)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.movieList_item -> {
                Toast.makeText(this, "sdf", Toast.LENGTH_LONG).show()
            }

            R.id.movieAPI_item -> {

            }
            R.id.movieReserve_item -> {

            }

            R.id.settings_item -> {

            }

        }
        return false
    }

}
