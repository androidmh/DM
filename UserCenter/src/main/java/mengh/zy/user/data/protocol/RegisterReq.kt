package mengh.zy.user.data.protocol

/**
 * Created by HDM on 2018/1/11.
 * E-mail menghedianmo@163.com
 * author HDM
 */

data class RegisterReq(private val nickname: String,private val account: String, private val secret: String, private val type: Int = 101)
