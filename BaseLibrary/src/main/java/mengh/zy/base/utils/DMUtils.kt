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

    /**
     * 判断是否是邮箱地址
     *
     * @param mail 待判断邮件地址
     * @return boolean
     */
    fun isMail(mail: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()
    }
}
