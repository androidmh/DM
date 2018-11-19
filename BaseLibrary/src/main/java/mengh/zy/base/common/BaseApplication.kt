package mengh.zy.base.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.bilibili.boxing.BoxingCrop
import com.bilibili.boxing.BoxingMediaLoader
import com.blankj.utilcode.util.Utils
import mengh.zy.base.injection.component.AppComponent
import com.orhanobut.hawk.Hawk
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import mengh.zy.base.R
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
class BaseApplication : MultiDexApplication() {
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
        Utils.init(this)
    }

    private fun isDebug(): Boolean {
        return true
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        init {
            //设置全局默认配置（优先级最低，会被其他设置覆盖）
            SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
                //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
                layout.setDragRate(0.5f)
                layout.setFooterHeight(30f)
                layout.setHeaderHeight(50f)
            }

            //全局设置默认的 Header
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setEnableHeaderTranslationContent(true)
                ClassicsHeader(context)
                        .setPrimaryColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                        .setAccentColor(ContextCompat.getColor(context, R.color.white))
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                layout.setEnableFooterTranslationContent(true)
                ClassicsFooter(context)
                        .setAccentColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }
        }
    }
}