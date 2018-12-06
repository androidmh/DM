package mengh.zy.user.ui.activity

import android.view.View
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.user.R
import org.jetbrains.anko.find

class PlayActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_play

    override fun initView() {
        initToolbar(find(R.id.dmToolbar), "玩转",true)
    }

    override fun widgetClick(v: View) {
    }

}
