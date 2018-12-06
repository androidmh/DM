package mengh.zy.user.ui.activity

import android.view.View
import kotlinx.android.synthetic.main.activity_setting.*
import mengh.zy.base.event.LoginEvent
import mengh.zy.base.ext.onClick
import mengh.zy.base.rx.DMBus
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.base.utils.MaterialDialogUtils
import mengh.zy.base.utils.UserHawkUtils
import mengh.zy.user.R
import org.jetbrains.anko.find

class SettingActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initView() {
        initToolbar(find(R.id.dmToolbar), "设置",true)
        mLogoutBtn.onClick(this)
    }

    override fun widgetClick(v: View) {
        when (v) {
            mLogoutBtn ->{
                MaterialDialogUtils.getConfirmDialog(this,"是否退出登录？","")
                        .positiveButton {
                            UserHawkUtils.deleteUserInfo()
                            DMBus.mBus.post(LoginEvent(true))
                            finish()
                        }
                        .show()
            }
        }
    }
}
