package global.msnthrp.feeed.dialogs.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseFragment
import global.msnthrp.feeed.dialogs.adapters.DialogsAdapter
import global.msnthrp.feeed.dialogs.viewmodels.DialogsViewModel
import global.msnthrp.feeed.dialogs.views.CommentAlertDialog
import global.msnthrp.feeed.models.Wrapper
import global.msnthrp.feeed.models.owner.Owner
import global.msnthrp.feeed.models.owner.User
import global.msnthrp.feeed.utils.showAlert
import global.msnthrp.feeed.utils.showToast
import kotlinx.android.synthetic.main.fragment_dialogs.*
import javax.inject.Inject

class DialogsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: DialogsViewModel.Factory
    private lateinit var viewModel: DialogsViewModel

    private val attachment by lazy { arguments?.getString(ARG_ATTACHMENT) }

    private val adapter by lazy {
        DialogsAdapter(safeContext, ::onClick)
    }

    override fun getLayoutId() = R.layout.fragment_dialogs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        App.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[DialogsViewModel::class.java]
        viewModel.getDialogs().observe(this, Observer { updateUi(it) })
        viewModel.getSent().observe(this, Observer { onSent() })
        viewModel.loadDialogs()
    }

    private fun updateUi(data: Wrapper<ArrayList<Owner>>) {
        if (data.data != null) {
            adapter.update(data.data)
        } else {
            showAlert(context, data.error ?: "unknown error")
        }
    }

    private fun onSent() {
        showToast(context, R.string.sent_successfully)
        activity?.finish()
    }

    private fun onClick(owner: Owner) {
        CommentAlertDialog(context ?: return) { comment ->
            viewModel.share(
                owner,
                attachment ?: return@CommentAlertDialog,
                comment
            )
        }.show()
    }

    private fun initRecyclerView() {
        rvDialogs.layoutManager = LinearLayoutManager(context)
        rvDialogs.adapter = adapter
    }

    companion object {

        const val ARG_ATTACHMENT = "attachment"

        fun newInstance(args: Bundle): DialogsFragment {
            val fragment = DialogsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}