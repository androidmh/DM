package mengh.zy.base.injection.component

import android.content.Context
import mengh.zy.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:全局Component
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun context() :Context
}