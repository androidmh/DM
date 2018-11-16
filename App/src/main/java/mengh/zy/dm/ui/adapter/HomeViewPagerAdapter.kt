package mengh.zy.dm.ui.adapter

import mengh.zy.base.ui.adapter.BaseFragmentPagerAdapter
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.dm.factory.FragmentFactory

class HomeViewPagerAdapter(activity: androidx.fragment.app.FragmentActivity) : BaseFragmentPagerAdapter<BaseFragment>(activity) {
    override fun init(fm: androidx.fragment.app.FragmentManager, list: MutableList<BaseFragment>) {
        list.add(FragmentFactory.createFragment(0))
        list.add(FragmentFactory.createFragment(1))
//        list.add(FragmentFactory.createFragment(2))
        list.add(FragmentFactory.createFragment(3))
    }
}