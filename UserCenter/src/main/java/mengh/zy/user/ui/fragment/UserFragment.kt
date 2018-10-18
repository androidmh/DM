package mengh.zy.user.ui.fragment

import android.view.View
import kotlinx.android.synthetic.main.fragment_user.*
import mengh.zy.base.common.BaseConstant
import mengh.zy.base.data.protocol.UserInfo
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.ext.onClick
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.base.utils.HawkUtils
import mengh.zy.provider.common.afterLogin
import mengh.zy.provider.common.isLogin
import mengh.zy.user.R
import mengh.zy.user.ui.activity.SettingActivity
import mengh.zy.user.ui.activity.UserInfoActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/28$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class UserFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_user

    override fun initView() {
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)
        mSettingTv.onClick(this)
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
                userInfo.user_icon?.let { it1 -> mUserIconIv.loadUrl(it1) }
            }
        } else {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
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
                startActivity<SettingActivity>()
            }
        }
    }

}