package mengh.zy.base.data.protocol

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:全局通用相应数据格式
 */

class BaseResp<out T>(val error_code: Int, val msg: String = "未知异常", val request: String, val data: T)