package mengh.zy.base.data.protocol

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/4$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
data class UserInfo(
        var access_token: String,
        var auth: Int = 1,
        var gender: Int = 1,
        var id: Int = 1,
        var nickname: String = "",
        var phone: String = "",
        var user_icon:String?="",
        var sign: String? = "",
        var user_back: String? = ""
)