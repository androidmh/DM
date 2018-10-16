package mengh.zy.dm.ui.activity

import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_welcome.*
import mengh.zy.base.common.BaseApplication.Companion.context
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.user.ui.activity.LoginActivity
import org.jetbrains.anko.startActivity
import java.util.*

class WelcomeActivity : BaseActivity() {
    override val layoutId: Int
        get() = mengh.zy.dm.R.layout.activity_welcome
    var recLen = 5
    var timer = Timer()

    private fun toMain() {
        startActivity<MainActivity>()
        task.cancel()
        finish()
    }

    private var task: TimerTask = object : TimerTask() {
        override fun run() {
            runOnUiThread {
                recLen--
                tv_wel.text = String.format(context.resources.getString(mengh.zy.dm.R.string.jump_times),recLen)
                if (recLen < 0) {
                    timer.cancel()
                    tv_wel.visibility = View.GONE
                    toMain()
                }
            }
        }
    }

    override fun initView() {
        tv_wel.setOnClickListener(this)
        val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, flag)
        timer.schedule(task, 1000, 1000)
    }

    override fun widgetClick(v: View) {
        when (v) {
            tv_wel -> toMain()
        }
    }

}
