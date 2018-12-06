package mengh.zy.user.ui.fragment

import android.app.Activity
import android.content.Intent
import com.google.android.material.appbar.AppBarLayout
import android.view.View
import com.afollestad.materialdialogs.list.listItems
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.entity.impl.ImageMedia
import kotlinx.android.synthetic.main.fragment_user.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.common.ResultCode
import mengh.zy.base.data.protocol.UserInfo
import mengh.zy.base.ext.*
import mengh.zy.base.ui.fragment.BaseMvpFragment
import mengh.zy.base.utils.*
import mengh.zy.provider.common.afterLogin
import mengh.zy.provider.common.isLogin
import mengh.zy.user.R
import mengh.zy.user.injection.component.DaggerUserComponent
import mengh.zy.user.injection.module.UserModule
import mengh.zy.user.presenter.UserPresenter
import mengh.zy.user.presenter.view.UserView
import mengh.zy.user.ui.activity.CollectActivity
import mengh.zy.user.ui.activity.PlayActivity
import mengh.zy.user.ui.activity.SettingActivity
import mengh.zy.user.ui.activity.UserInfoActivity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/28$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class UserFragment : BaseMvpFragment<UserPresenter>(), UserView {
    override val layoutId: Int
        get() = R.layout.fragment_user

    override fun initView() {
        initToolbar(dmToolbar, "我的")
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)
        mSettingTv.onClick(this)
        mPlayTv.onClick(this)
        collectTv.onClick(this)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            if (verticalOffset == 0) {
//                tvTitle.alpha = 0.0f
            } else {
                val alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRange) * 10) / 10
//                tvTitle.alpha = alpha.toFloat()
            }
        })
        backIv.onLongClick {
            MaterialDialogUtils.getBasicDialog(mActivity)
                    .listItems(items = listOf("更换背景")) { _, _, _ ->
                        afterLogin {
                            BoxingUtils.selectSingleCutImg(this, ResultCode.REQUEST_CODE)
                        }
                    }
                    .show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val medias = Boxing.getResult(data)
        if (resultCode == Activity.RESULT_OK && requestCode == ResultCode.REQUEST_CODE) {
            val imageMedia = medias?.get(0) as ImageMedia
            val file = imageMedia.getCompressFile(mActivity)
            val imageBody = RequestBody.create(null, file)
            val createFormData = MultipartBody.Part.createFormData("back", file.name, imageBody)
            mPresenter.updateBack(createFormData)
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        if (isLogin()) {
            val userInfo = HawkUtils.getObj<UserInfo>(BaseConstant.USER_INFO)
            userInfo?.let {
                mUserNameTv.text = it.nickname
                userInfo.user_icon?.let { it1 ->
                    mUserIconIv.loadCircleUrl(it1)
                }
                userInfo.user_back?.let { it2 ->
                    backIv.loadUrl(it2)
                }
            }
        } else {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
            backIv.setImageResource(R.drawable.bg_me_header)
        }
    }

    override fun widgetClick(v: View) {
        when (v) {
            mUserIconIv, mUserNameTv -> {
                afterLogin {
                    startActivity<UserInfoActivity>()
                }
            }
            mSettingTv -> {
                afterLogin {
                    startActivity<SettingActivity>()
                }
            }
            mPlayTv -> {
                afterLogin {
                    startActivity<PlayActivity>()
                }
            }
            collectTv ->{
                afterLogin {
                    startActivity<CollectActivity>()
                }
            }
        }
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUpdateBackResult(result: String) {
        UserHawkUtils.putUserBack(result)
        loadData()
        toast("上传成功")
    }

}
