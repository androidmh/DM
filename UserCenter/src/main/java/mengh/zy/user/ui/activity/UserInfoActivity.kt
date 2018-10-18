package mengh.zy.user.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.entity.impl.ImageMedia
import kotlinx.android.synthetic.main.activity_user_info.*
import mengh.zy.base.ext.onClick
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.user.R
import mengh.zy.base.common.ResultCode.Companion.REQUEST_CODE
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.utils.BoxingUtils


class UserInfoActivity : BaseActivity() {


    override val layoutId: Int
        get() = R.layout.activity_user_info

    override fun initView() {
        mHeaderBar.getRightView().onClick(this)
        mUserIconView.onClick(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val medias = Boxing.getResult(data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            mUserIconIv.loadUrl((medias?.get(0) as ImageMedia).thumbnailPath)
        }
    }

    override fun widgetClick(v: View) {
        when (v.id) {
            R.id.tvRight -> {
            }
            R.id.mUserIconView -> {
                BoxingUtils.selectSingleCutImg(this, REQUEST_CODE)
            }
        }
    }

}
