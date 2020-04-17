package luv.zoey.edwith_pr.MainMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_menu.*
import luv.zoey.edwith_pr.R

class MenuActivity : AppCompatActivity() {


    var fragList: MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val viewPagerAdapter = MenuViewPagerAdapter(supportFragmentManager).apply {
            addItem(TestFragment())
            addItem(TestFragment2())
        }

        menu_ViewPager.adapter = viewPagerAdapter


    }


}
