package mengh.zy.dm.data.protocol

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/10/25$.
 * PS: Not easy to write code, please indicate.
 *
 *
 * Describe:
 */
class IndexBean(var banners: List<BannersBean>, var indexes: List<IndexesBean>) {
    class BannersBean {
        var describe: String? = null
        var url: String? = null
    }

    class IndexesBean {
        var ad: Int = 0
        var describe: String? = null
        var title: String? = null
        var url: String? = null
    }
}
