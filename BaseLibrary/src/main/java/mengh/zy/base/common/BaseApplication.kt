package mengh.zy.base.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.bilibili.boxing.BoxingCrop
import com.bilibili.boxing.BoxingMediaLoader
import mengh.zy.base.injection.component.AppComponent
import com.orhanobut.hawk.Hawk
import mengh.zy.base.injection.component.DaggerAppComponent
import mengh.zy.base.injection.module.AppModule
import mengh.zy.base.widgets.BoxingGlideLoader
import mengh.zy.base.widgets.BoxingUcrop

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class BaseApplication : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        context = this

        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        Hawk.init(this).build()
        BoxingMediaLoader.getInstance().init(BoxingGlideLoader())
        BoxingCrop.getInstance().init(BoxingUcrop())
    }

    private fun isDebug(): Boolean {
        return true
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    companion object {
        lateinit var context:Context
    }
}