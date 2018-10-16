package mengh.zy.base.injection.component

import android.app.Activity
import android.content.Context
import mengh.zy.base.injection.ActivityScope
import mengh.zy.base.injection.module.ActivityModule
import mengh.zy.base.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:全局Activity Component
 */
@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(ActivityModule::class), (LifecycleProviderModule::class)])
interface ActivityComponent {
    fun activity(): Activity
    fun context() : Context
    fun lifecycleProvider(): LifecycleProvider<*>
}