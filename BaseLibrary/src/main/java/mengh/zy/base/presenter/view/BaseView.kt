package mengh.zy.base.presenter.view

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
}