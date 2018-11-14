package mengh.zy.base.utils

import mengh.zy.base.common.BaseConstant
import mengh.zy.base.common.BaseConstant.Companion.BASE_TOKEN
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
        val info = HawkUtils.getObj<UserInfo>(USER_INFO)
        if (info != null) {
            return info.access_token
        }
        return BASE_TOKEN
    }

    fun deleteUserInfo(){
        HawkUtils.deleteObj(USER_INFO)
    }

    fun putUserInfo(gender: Int, nickname: String, sign: String) {
        val info = HawkUtils.getObj<UserInfo>(USER_INFO)
        info?.gender = gender
        info?.nickname = nickname
        info?.sign = sign
        HawkUtils.putObj(USER_INFO,info)
    }

    fun putUserAvatar(user_icon: String) {
        val info = HawkUtils.getObj<UserInfo>(USER_INFO)
        info?.user_icon = user_icon
        HawkUtils.putObj(USER_INFO,info)
    }

    fun putUserBack(back: String) {
        val info = HawkUtils.getObj<UserInfo>(USER_INFO)
        info?.user_back = back
        HawkUtils.putObj(USER_INFO,info)
    }
}