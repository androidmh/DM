package mengh.zy.base.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import mengh.zy.base.R


object MaterialDialogUtils {
    fun getConfirmDialog(context: Context, title: String, message: String): MaterialDialog {
        return MaterialDialog(context)
                .title(text = title)
                .message(text = message)
                .positiveButton(R.string.agree)
                .negativeButton(R.string.disagree)
    }

    fun getBasicDialog(context: Context, title: String = ""): MaterialDialog {
        return if (title.isNotBlank()) {
            MaterialDialog(context)
                    .title(text = title)
        } else {
            MaterialDialog(context)
        }
    }
}
