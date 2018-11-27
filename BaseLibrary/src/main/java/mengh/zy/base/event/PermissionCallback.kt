package mengh.zy.base.event

import com.blankj.utilcode.util.PermissionUtils

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/22$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
interface PermissionCallback {
    interface RationaleCallback:PermissionUtils.OnRationaleListener{
        override fun rationale(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest?)
    }

    interface EmptyCallback:PermissionUtils.FullCallback{
    }
}