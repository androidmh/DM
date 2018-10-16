package mengh.zy.dm.ui.activity

import mengh.zy.base.ui.activity.BaseActivity
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import mengh.zy.dm.R
import mengh.zy.dm.factory.FragmentFactory

class MainActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
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
        val manger = supportFragmentManager.beginTransaction()
        manger.replace(R.id.mBody, FragmentFactory.createFragment(position)!!)
        manger.commit()
    }

    override fun widgetClick(v: View) {
    }
}
