package mengh.zy.base.rx

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:异常基类
 *   @param status 状态码
 *   @param msg 错误信息
 */

class BaseException(private val status: Int, private val msg: String) : Throwable()