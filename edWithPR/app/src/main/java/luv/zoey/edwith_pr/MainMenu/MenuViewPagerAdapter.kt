package luv.zoey.edwith_pr.MainMenu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MenuViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var items: ArrayList<Fragment> = arrayListOf()

    fun addItem(item: Fragment) {
        items.add(item)
    }

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }
}