package mengh.zy.user.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.entity.impl.ImageMedia
import kotlinx.android.synthetic.main.activity_user_info.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.ext.onClick
import mengh.zy.user.R
import mengh.zy.base.common.ResultCode.Companion.REQUEST_CODE
import mengh.zy.base.data.protocol.UserInfo
import mengh.zy.base.ext.getToString
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.ui.activity.BaseMvpActivity
import mengh.zy.base.utils.BoxingUtils
import mengh.zy.base.utils.HawkUtils
import mengh.zy.base.utils.UserHawkUtils
import mengh.zy.user.data.protocol.UpdateUserReq
import mengh.zy.user.injection.component.DaggerUserComponent
import mengh.zy.user.injection.module.UserModule
import mengh.zy.user.presenter.UpdateUserPresenter
import mengh.zy.user.presenter.view.UpdateUserView
import okhttp3.MultipartBody
import org.jetbrains.anko.toast
import java.io.File
import okhttp3.RequestBody






class UserInfoActivity : BaseMvpActivity<UpdateUserPresenter>(), UpdateUserView {
    override val layoutId: Int
        get() = R.layout.activity_user_info

    override fun initView() {
        mHeaderBar.getRightView().onClick(this)
        mUserIconView.onClick(this)
        initData()
    }


    private fun initData() {
        val userInfo = HawkUtils.getObj<UserInfo>(BaseConstant.USER_INFO)
        userInfo?.let {
            mUserIconIv.loadUrl(it.user_icon)
            mUserNameEt.setText(it.nickname)
            if (it.gender == 1) {
                mGenderMaleRb.isChecked = true
            } else {
                mGenderFemaleRb.isChecked = true
            }
            mUserMobileTv.text = it.phone
            mUserSignEt.setText(it.sign)
        }
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val medias = Boxing.getResult(data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val imageMedia = medias?.get(0) as ImageMedia
            val file = File(imageMedia.thumbnailPath)
            val imageBody = RequestBody.create(null, file)
            val createFormData = MultipartBody.Part.createFormData("avatar", file.name, imageBody)
            mPresenter.updateAvatar(createFormData)
        }
    }

    override fun onUpdateAvatarResult(result: String) {
        toast("上传成功")
        mUserIconIv.loadUrl(result)
        UserHawkUtils.putUserAvatar(result)
    }

    override fun onUpdateUserResult(result: String) {
        toast(result)
        val gender = if (mGenderMaleRb.isChecked) 1 else 2
        UserHawkUtils.putUserInfo(gender, mUserNameEt.getToString(), mUserSignEt.getToString())
    }

    override fun widgetClick(v: View) {
        when (v.id) {
            R.id.tvRight -> {
                if (checkInput()) {
                    val gender = if (mGenderMaleRb.isChecked) 1 else 2
                    mPresenter.updateUser(UpdateUserReq(gender, mUserNameEt.getToString(), mUserSignEt.getToString()))
                } else {
                    toast("您未做任何更改")
                }
            }
            R.id.mUserIconView -> {
                BoxingUtils.selectSingleCutImg(this, REQUEST_CODE)
            }
        }
    }

    private fun checkInput(): Boolean {
        val userInfo = HawkUtils.getObj<UserInfo>(BaseConstant.USER_INFO)
        val gender = if (mGenderMaleRb.isChecked) 1 else 2
        return !(userInfo?.gender == gender && userInfo.nickname == mUserNameEt.getToString() && userInfo.sign == mUserSignEt.getToString())
    }
}
