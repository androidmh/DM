package mengh.zy.base.utils

import java.util.*

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/20$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
object DMTimeUtils {
    fun startTimeMethod(method: ()->Unit, time: Long): Timer {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                method()
            }
        }, time)
        return timer
    }

    fun startTimeMethod(method: ()->Unit, times: Long, time: Long): Timer {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                method()
            }
        }, time,time)
        return timer
    }
}