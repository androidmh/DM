package mengh.zy.base.ext

import android.os.Build


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/19$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:判断sdk版本的全局方法
 */

/*
    判断版本号21
 */
fun judgeSdk21(isMethod: () -> Unit,elseMethod2: () -> Unit={}) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        isMethod()
    } else {
        elseMethod2()
    }
}