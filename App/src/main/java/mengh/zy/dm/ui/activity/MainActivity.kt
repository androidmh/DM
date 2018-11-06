package mengh.zy.dm.ui.activity

import android.content.Intent
import android.view.KeyEvent
import mengh.zy.base.ui.activity.BaseActivity
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import mengh.zy.dm.R
import mengh.zy.dm.ui.adapter.HomeViewPagerAdapter

class MainActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        val adapter = HomeViewPagerAdapter(this)
        mainVp.adapter = adapter
        mainVp.offscreenPageLimit = adapter.count
        mainVp.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                mBottomNavBar.selectTab(p0)
            }

        })
        changeFragment(0)
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
        })
    }

    private fun changeFragment(position: Int) {
        mainVp.currentItem = position
    }

    override fun widgetClick(v: View) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent()
            intent.action = "android.intent.action.MAIN"
            intent.addCategory("android.intent.category.HOME")
            startActivity(intent)
        }
        return false
    }
}
