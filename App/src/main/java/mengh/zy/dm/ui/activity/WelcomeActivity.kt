package mengh.zy.dm.ui.activity

import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_welcome.*
import mengh.zy.base.common.BaseApplication.Companion.context
import mengh.zy.base.ext.setVisible
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.base.utils.DMTimeUtils
import org.jetbrains.anko.startActivity
import java.util.*

class WelcomeActivity : BaseActivity() {
    override val layoutId: Int
        get() = mengh.zy.dm.R.layout.activity_welcome
    private var recLen:Long = 5
    private lateinit var timer:Timer

    override fun initView() {
//        timeTv.setOnClickListener(this)
//        timer = DMTimeUtils.startTimeMethod({
//            runOnUiThread {
//                recLen--
//                timeTv.text = String.format(context.resources.getString(mengh.zy.dm.R.string.jump_times),recLen)
//                if (recLen < 0) {
//                    timer.cancel()
//                    timeTv.setVisible(false)
//                    toMain()
//                }
//            }
//        }, recLen*1000,1000)
        toMain()
        val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, flag)
    }

    override fun widgetClick(v: View) {
        when (v) {
            timeTv -> toMain()
        }
    }

    private fun toMain() {
        startActivity<MainActivity>()
//        timer.cancel()
        finish()
    }

}
