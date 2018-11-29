package mengh.zy.base.utils

import com.blankj.utilcode.util.LogUtils

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/27$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:全局Log
 */
object DMLog {
    fun LogV(tag: String, content: Any) {
        LogUtils.vTag(tag, content)
    }
}