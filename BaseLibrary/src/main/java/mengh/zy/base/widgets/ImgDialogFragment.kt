package mengh.zy.base.widgets

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_img.*
import mengh.zy.base.R
import mengh.zy.base.ext.loadUrl
import org.jetbrains.anko.find

@SuppressLint("ValidFragment")
/**
 * Created by HMH on 2017/8/17.
 */

class ImgDialogFragment(private var url: String) : DialogFragment() {

    override fun onStart() {
        super.onStart()
        if (dialog == null) {
            return
        }

        dialog.window!!.setWindowAnimations(
                R.style.DialogAnimation)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme)
    }

    override fun onResume() {
        super.onResume()
        //动态设置dialog大小
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        val v = inflater.inflate(R.layout.dialog_img,ll_iv_dialog)
        val img = v.find<ImageView>(R.id.imgIv)
        img.loadUrl(url)
        img.setOnClickListener {
            this.dismiss()
        }
        return v
    }


}
