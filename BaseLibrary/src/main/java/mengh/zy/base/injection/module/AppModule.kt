package mengh.zy.base.injection.module

import android.content.Context
import dagger.Module
import dagger.Provides
import mengh.zy.base.common.BaseApplication
import javax.inject.Singleton

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:全局Module
 */
@Module
class AppModule(private val context: BaseApplication) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }
}