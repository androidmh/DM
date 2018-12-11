package mengh.zy.base.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
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

    fun getCustomDialogs(context: Context, title: String, layout: Int): MaterialDialog {
        return MaterialDialog(context)
                .customView(layout, scrollable = true)
                .negativeButton(R.string.disagree)
    }

}
