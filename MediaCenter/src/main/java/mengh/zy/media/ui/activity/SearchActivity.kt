package mengh.zy.media.ui.activity

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*
import mengh.zy.base.ext.judgeSdk21
import mengh.zy.base.ui.activity.BaseActivity
import mengh.zy.media.R

class SearchActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        judgeSdk21({
            val explode = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
            window.enterTransition = explode
            window.exitTransition = explode
            window.reenterTransition = explode
        })

    }

    override fun initView() {
        mToolbar.setNavigationOnClickListener {
            judgeSdk21({
                finishAfterTransition()
            },{
                finish()
            })
        }
    }

    override fun widgetClick(v: View) {
    }
}
