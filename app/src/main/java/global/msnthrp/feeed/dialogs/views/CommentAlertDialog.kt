package global.msnthrp.feeed.dialogs.views

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import global.msnthrp.feeed.R
import global.msnthrp.feeed.utils.Styler
import global.msnthrp.feeed.utils.asText
import kotlinx.android.synthetic.main.dialog_comment.view.*

class CommentAlertDialog(
    context: Context,
    private val onCommentAdded: (String) -> Unit) : AlertDialog(context) {

    init {
        val view = View.inflate(context, R.layout.dialog_comment, null)
        setView(view)
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok)) { _, _ ->
            onCommentAdded(view.etComment.asText())
            dismiss()
        }
    }

    override fun show() {
        super.show()
        Styler.dialog(this)
    }
}