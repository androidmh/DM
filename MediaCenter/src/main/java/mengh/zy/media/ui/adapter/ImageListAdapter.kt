package mengh.zy.media.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import mengh.zy.base.ext.loadUrl
import mengh.zy.media.R
import mengh.zy.media.data.protocol.ImageBean

class ImageListAdapter(layoutResId: Int, data: List<ImageBean.ImagesBean>) : BaseQuickAdapter<ImageBean.ImagesBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: ImageBean.ImagesBean) {
        val imgIv = helper!!.getView<ImageView>(R.id.imgIv)
        imgIv.loadUrl(item.url!!)
    }
}