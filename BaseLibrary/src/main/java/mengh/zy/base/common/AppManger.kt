package mengh.zy.base.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/13$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class AppManger private constructor() {

    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance: AppManger by lazy {
            AppManger()
        }
    }

    /**
     * 入栈
     */
    fun addActivity(activity: Activity){
        activityStack.add(activity)
    }

    /**
     * 出栈
     */
    fun finishActivity(activity: Activity){
        activity.finish()
        activityStack.remove(activity)
    }

    /**
     * 获取栈顶
     */
    fun currentActivity():Activity{
        return activityStack.lastElement()
    }

    /**
     * 清理栈
     */
    fun finishAllActivity(){
        for (activity in activityStack){
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun exitApp(context: Context){
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}