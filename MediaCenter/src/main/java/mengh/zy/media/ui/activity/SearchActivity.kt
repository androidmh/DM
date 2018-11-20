package mengh.zy.media.ui.activity

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.activity_search.*
import mengh.zy.base.ext.judgeSdk21
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.base.utils.DMTimeUtils
import mengh.zy.media.R

class SearchActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        judgeSdk21({
            val transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
            window.enterTransition = transition
            window.exitTransition = transition
            window.reenterTransition = transition
        })

    }

    override fun initView() {
        val timer = DMTimeUtils.startTimeMethod({
            runOnUiThread {
                KeyboardUtils.showSoftInput(searchEt)
            }
        }, 500)
        mToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(searchEt)
            timer.cancel()
            judgeSdk21({
                finishAfterTransition()
            }, {
                finish()
            })
        }
    }

    override fun widgetClick(v: View) {
    }
}
