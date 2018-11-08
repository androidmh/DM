package mengh.zy.dm.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.Banner
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.widgets.GlideImageLoader
import mengh.zy.dm.R
import mengh.zy.dm.ui.item.IndexItem
import java.util.*


/**
 * Author by HDM, Email menghedianmo@163.com, Date on 2018/4/10.
 * PS: Not easy to write code, please indicate.
 */

class IndexAdapter(mData: MutableList<IndexItem>?) : BaseMultiItemQuickAdapter<IndexItem, BaseViewHolder>(mData) {
    init {
        addItemType(IndexItem.IMG, R.layout.item_index_vp)
        addItemType(IndexItem.LAYOUT, R.layout.item_index)
    }

    override fun convert(helper: BaseViewHolder?, item: IndexItem) {
        when (helper?.itemViewType) {
            IndexItem.IMG -> {
                val localImages = ArrayList<String>()
                val imgData = item.getImgData()
                for (img in imgData!!){
                    localImages.add(img.url!!)
                }
                val banner = helper.getView(R.id.banner) as Banner
                banner.setImageLoader(GlideImageLoader())
                //设置图片集合
                banner.setImages(localImages)
                //banner设置方法全部调用完毕时最后调用
                banner.start()
            }
            IndexItem.LAYOUT -> {
                helper.setText(R.id.titleTv,item.getIndexData()!!.title)
                helper.setText(R.id.describeTv,item.getIndexData()!!.describe)
                val localIv = helper.getView<ImageView>(R.id.localIv)
                localIv.loadUrl(item.getIndexData()!!.url!!)
                val adTv = helper.getView<TextView>(R.id.adTv)
                if (item.getIndexData()!!.ad==2){
                    adTv.text = "广告"
                }
            }
        }

    }
}