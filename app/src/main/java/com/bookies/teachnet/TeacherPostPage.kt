package com.bookies.teachnet
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.util.*

class TeacherPostPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_post_page)
        val Main2ToolBar = findViewById<Toolbar>(R.id.main_two_toolbar)
        setSupportActionBar(Main2ToolBar)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val Main2ViewPager = findViewById<ViewPager>(R.id.main_2_view_pager)
        tabLayout.setupWithViewPager(Main2ViewPager)
        val notCompletedFragment = NotCompletedFragment()
        val completedFragment=CompletedFragment()
        val tabPageAdapter =
            Main2TabPageAdapter(supportFragmentManager, 0)
        tabPageAdapter.addFragment(notCompletedFragment, "Not Completed")
        tabPageAdapter.addFragment(completedFragment,"Completed")
        Main2ViewPager.adapter = tabPageAdapter
    }
    internal class Main2TabPageAdapter(
        fm: FragmentManager,
        behavior: Int
    ) :
        FragmentPagerAdapter(fm, behavior) {
        var tabFragmentList: MutableList<Fragment> =
            ArrayList()
        var tabTitleList: MutableList<String> =
            ArrayList()

        fun addFragment(
            fragment: Fragment,
            title: String
        ) {
            tabFragmentList.add(fragment)
            tabTitleList.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return tabFragmentList[position]
        }

        override fun getCount(): Int {
            return tabFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitleList[position]
        }
    }
}