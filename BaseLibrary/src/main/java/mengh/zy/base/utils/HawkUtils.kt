package mengh.zy.base.utils

import com.orhanobut.hawk.Hawk


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/3$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
object HawkUtils {
    fun <T> putObj(key: String, t: T): Boolean {
        return Hawk.put(key, t)
    }

    fun <T> getObj(key: String): T? {
        return Hawk.get(key, null)
    }

    fun deleteObj(key: String): Boolean {
        return Hawk.delete(key)
    }

}