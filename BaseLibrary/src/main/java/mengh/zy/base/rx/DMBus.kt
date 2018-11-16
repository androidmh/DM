package mengh.zy.base.rx

import com.hwangjr.rxbus.Bus

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/16$.
 * PS: Not easy to write code, please indicate.
 *
 *
 * Describe:事件总栈
 */
class DMBus private constructor() {
    companion object {
        val mBus: Bus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Bus() }
    }
}
