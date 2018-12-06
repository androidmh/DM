package mengh.zy.user.ui.activity

import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import mengh.zy.base.ext.enable
import mengh.zy.base.ext.getToString
import mengh.zy.base.ext.onClick
import mengh.zy.base.ui.activity.BaseMvpActivity
import mengh.zy.base.utils.DMUtils.isBtnEnableOfEt
import mengh.zy.user.R
import mengh.zy.user.data.protocol.RegisterReq
import mengh.zy.user.injection.component.DaggerUserComponent
import mengh.zy.user.injection.module.UserModule
import mengh.zy.user.presenter.RegisterPresenter
import mengh.zy.user.presenter.view.RegisterView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/28$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {
    override val layoutId: Int
        get() = R.layout.activity_register


    override fun initView() {
        initToolbar(find(R.id.dmToolbar), "注册", true)
        registerBtn.enable({ isBtnEnable() }, nickNameEt, userPhoneEt, psdEt)
        registerBtn.onClick {
            mPresenter.register(RegisterReq(nickNameEt.getToString(), userPhoneEt.getToString(), psdEt.getToString()))
        }
    }

    override fun initData() {
    }

    override fun widgetClick(v: View) {

    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRegisterResult(result: String) {
        toast(result)
        finish()
    }

    private fun isBtnEnable(): Boolean {
        return isBtnEnableOfEt(nickNameEt, userPhoneEt, psdEt)
    }
}
