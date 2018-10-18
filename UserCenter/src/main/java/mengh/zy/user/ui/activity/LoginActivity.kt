package mengh.zy.user.ui.activity

import android.util.Base64
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_login.*
import mengh.zy.base.common.BaseConstant.Companion.USER_INFO
import mengh.zy.base.common.BaseConstant.Companion.USER_PSD
import mengh.zy.base.data.protocol.UserInfo
import mengh.zy.base.data.protocol.UserPsd
import mengh.zy.base.ext.enable
import mengh.zy.base.ext.getToString
import mengh.zy.base.ext.onClick
import mengh.zy.base.ui.activity.BaseMvpActivity
import mengh.zy.base.utils.DMUtils
import mengh.zy.base.utils.HawkUtils
import mengh.zy.provider.router.RouterPath.UserCenter.Companion.PATH_LOGIN
import mengh.zy.user.R
import mengh.zy.user.data.protocol.GetTokenReq
import mengh.zy.user.data.protocol.UserInfoBean
import mengh.zy.user.injection.component.DaggerUserComponent
import mengh.zy.user.injection.module.UserModule
import mengh.zy.user.presenter.LoginPresenter
import mengh.zy.user.presenter.view.LoginView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/28$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
@Route(path = PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initView() {
        loginBtn.enable({ isBtnEnable() }, userPhoneEt, psdEt)
        val userPsd = HawkUtils.getObj<UserPsd>(USER_PSD)
        userPsd?.let { it ->
            userPhoneEt.setText(it.username)
            psdEt.setText(it.password)
            rememberCb.isChecked = it.password != ""
        }
        mHeaderBar.getRightView().onClick(this)
        loginBtn.onClick(this)
    }

    override fun widgetClick(v: View) {
        when (v.id) {
            R.id.tvRight -> startActivity<RegisterActivity>()

            R.id.loginBtn -> {
                if (userPhoneEt.getToString().length != 11) {
                    toast("请输入正确的手机号")
                    return
                }
                mPresenter.getToken(GetTokenReq(userPhoneEt.getToString(), psdEt.getToString()))
            }
        }
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onLoginResult(result: String) {
        val token: String = "Basic " + Base64.encodeToString("$result:".toByteArray(), Base64.NO_WRAP)
        HawkUtils.putObj(USER_INFO, UserInfo(token))

        if (rememberCb.isChecked) {
            HawkUtils.putObj(USER_PSD, UserPsd(userPhoneEt.getToString(), psdEt.getToString()))
        } else {
            HawkUtils.putObj(USER_PSD, UserPsd("", ""))
        }
        mPresenter.getUser()
    }

    override fun onGetUserResult(result: UserInfoBean) {
        val userInfo = HawkUtils.getObj<UserInfo>(USER_INFO)
        userInfo?.auth = result.auth
        userInfo?.gender = result.gender
        userInfo?.id = result.id
        userInfo?.nickname = result.nickname
        userInfo?.phone = result.phone
        userInfo?.user_icon = result.user_icon
        userInfo?.sign = result.sign
        HawkUtils.putObj(USER_INFO, userInfo)
        toast("登录成功")
        finish()
    }

    private fun isBtnEnable(): Boolean {
        return DMUtils.isBtnEnableOfEt(userPhoneEt, psdEt)
    }

}
