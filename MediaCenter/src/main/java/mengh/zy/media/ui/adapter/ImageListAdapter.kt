package mengh.zy.media.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sackcentury.shinebuttonlib.ShineButton
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.ext.setVisible
import mengh.zy.media.R
import mengh.zy.media.data.protocol.ImageBean
import mengh.zy.provider.common.isLogin

class ImageListAdapter(layoutResId: Int, data: List<ImageBean.ImagesBean>) : BaseQuickAdapter<ImageBean.ImagesBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: ImageBean.ImagesBean) {
        val imgIv = helper.getView<ImageView>(R.id.imgIv)
        val collectBtn = helper.getView<ShineButton>(R.id.collectBtn)
        helper.addOnClickListener(R.id.collectBtn)
        helper.addOnClickListener(R.id.authorTv)

        helper.setText(R.id.desTv,item.describe)
        helper.setText(R.id.authorTv,item.author.nickname)
        imgIv.loadUrl(item.url)
        collectBtn.isChecked = item.is_collect
        if (!isLogin()) {
            collectBtn.setVisible(false)
        }
    }
}