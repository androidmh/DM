package mengh.zy.base.utils

import mengh.zy.base.common.BaseConstant.Companion.USER_INFO
import mengh.zy.base.data.protocol.UserInfo

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/10/16$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
object UserHawkUtils {
    fun getUserToken(): String {
        val a = HawkUtils.getObj<UserInfo>(USER_INFO)
        if (a != null) {
            return a.access_token
        }
        return ""
    }

    fun deleteUserInfo(){
        HawkUtils.deleteObj(USER_INFO)
    }
}