package mengh.zy.dm.ui.item

import com.chad.library.adapter.base.entity.MultiItemEntity
import mengh.zy.dm.data.protocol.IndexBean

/**
 * Author by HDM, Email menghedianmo@163.com, Date on 2018/4/11.
 * PS: Not easy to write code, please indicate.
 */
class IndexItem (private val itemType: Int, private val data: IndexBean.IndexesBean? =null, private val data2: MutableList<IndexBean.BannersBean>? =null): MultiItemEntity{
    companion object {
        const val IMG = 1
        const val  LAYOUT= 2
    }

    override fun getItemType(): Int {
        return itemType
    }

    fun getIndexData(): IndexBean.IndexesBean? {
        return data
    }

    fun getImgData(): MutableList<IndexBean.BannersBean>? {
        return data2
    }
}