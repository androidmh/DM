package mengh.zy.media.data.protocol

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/10/25$.
 * PS: Not easy to write code, please indicate.
 *
 *
 * Describe:
 */
data class ImageBean(var images: List<ImagesBean>,
                     var next_page: Int) {

    class ImagesBean(var describe: String,
                     var id: Int,
                     var author: AuthorBean,
                     var is_collect: Boolean,
                     var url: String)

    class AuthorBean(var id:Int,var nickname:String, var sign:String)
}
