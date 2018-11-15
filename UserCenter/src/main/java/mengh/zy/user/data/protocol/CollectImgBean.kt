package mengh.zy.user.data.protocol

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/15$.
 * PS: Not easy to write code, please indicate.
 *
 *
 * Describe:
 */
data class CollectImgBean(var next_page: Int,
                          var images: List<ImagesBean>) {

    class ImagesBean(var describe: String,
                     var id: Int,
                     var label: String,
                     var sort: String,
                     var url: String)
}
