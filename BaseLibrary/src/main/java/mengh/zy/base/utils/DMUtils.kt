package mengh.zy.base.utils

import android.widget.EditText

/**
 * Created by HMH on 2017/7/31.
 */

object DMUtils {

    fun isBtnEnableOfEt(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.isNullOrEmpty()){
                return false
            }
        }
        return true
    }
}
