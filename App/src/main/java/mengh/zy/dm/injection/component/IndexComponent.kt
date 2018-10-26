package mengh.zy.dm.injection.component

import dagger.Component
import mengh.zy.dm.injection.module.IndexModule
import mengh.zy.base.injection.PerComponentScope
import mengh.zy.base.injection.component.ActivityComponent
import mengh.zy.dm.ui.fragment.IndexFragment

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:user的联系类
 */
@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(IndexModule::class)])
interface IndexComponent {
    fun inject(fragment: IndexFragment)
}