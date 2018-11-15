package mengh.zy.user.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import mengh.zy.base.ext.loadUrl
import mengh.zy.user.R
import mengh.zy.user.data.protocol.CollectImgBean

class CollectImgAdapter(layoutResId: Int, data: List<CollectImgBean.ImagesBean>) : BaseQuickAdapter<CollectImgBean.ImagesBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: CollectImgBean.ImagesBean) {
        val imgIv = helper.getView<ImageView>(R.id.imgIv)
        imgIv.loadUrl(item.url)
    }
}