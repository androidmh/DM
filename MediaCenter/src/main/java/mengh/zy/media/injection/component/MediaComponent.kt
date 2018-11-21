package mengh.zy.media.injection.component

import dagger.Component
import mengh.zy.media.injection.module.MediaModule
import mengh.zy.base.injection.PerComponentScope
import mengh.zy.base.injection.component.ActivityComponent
import mengh.zy.media.ui.activity.SearchActivity
import mengh.zy.media.ui.activity.SearchListActivity
import mengh.zy.media.ui.fragment.ImgListFragment

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:user的联系类
 */
@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(MediaModule::class)])
interface MediaComponent {
    fun inject(fragment: ImgListFragment)
    fun inject(activity: SearchActivity)
    fun inject(activity: SearchListActivity)
}