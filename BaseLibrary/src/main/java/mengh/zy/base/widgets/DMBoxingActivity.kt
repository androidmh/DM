package mengh.zy.base.widgets

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bilibili.boxing.AbsBoxingActivity
import com.bilibili.boxing.AbsBoxingViewFragment
import com.bilibili.boxing.model.config.BoxingConfig
import com.bilibili.boxing.model.entity.BaseMedia
import java.util.ArrayList

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/11/29$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class DMBoxingActivity : AbsBoxingActivity() {

    private  var mPickerFragment: DMBoxingViewFragment? = null

    override fun onCreateBoxingView(medias: ArrayList<BaseMedia>?): AbsBoxingViewFragment {
        mPickerFragment = (supportFragmentManager.findFragmentByTag(DMBoxingViewFragment.TAG) as? DMBoxingViewFragment?)
        if (mPickerFragment == null) {
            mPickerFragment = DMBoxingViewFragment.newInstance().setSelectedBundle(medias) as DMBoxingViewFragment
            supportFragmentManager.beginTransaction().replace(com.bilibili.boxing_impl.R.id.content_layout, mPickerFragment!!, DMBoxingViewFragment.TAG).commit()
        }
        return mPickerFragment!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.bilibili.boxing_impl.R.layout.activity_boxing)
        createToolbar()
        setTitleTxt(boxingConfig)
    }

    private fun createToolbar() {
        val bar = findViewById<View>(com.bilibili.boxing_impl.R.id.nav_top_bar) as Toolbar
        setSupportActionBar(bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        bar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setTitleTxt(config: BoxingConfig) {
        val titleTxt = findViewById<TextView>(com.bilibili.boxing_impl.R.id.pick_album_txt)
        if (config.mode == BoxingConfig.Mode.VIDEO) {
            titleTxt.setText(com.bilibili.boxing_impl.R.string.boxing_video_title)
            titleTxt.setCompoundDrawables(null, null, null, null)
            return
        }
        mPickerFragment?.setTitleTxt(titleTxt)
    }

    override fun onBoxingFinish(intent: Intent?, medias: MutableList<BaseMedia>?) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}