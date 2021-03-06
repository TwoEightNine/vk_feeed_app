package global.msnthrp.feeed.feed.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import global.msnthrp.feeed.R
import global.msnthrp.feeed.feed.viewmodel.GroupFeedViewModel
import global.msnthrp.feeed.models.Wrapper
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.utils.AppBarStateChangeListener
import global.msnthrp.feeed.utils.load
import global.msnthrp.feeed.utils.setVisible
import global.msnthrp.feeed.utils.showToast
import kotlinx.android.synthetic.main.fragment_group.*

class GroupFeedFragment : FeedFragment() {

    private val group by lazy {
        arguments?.getParcelable<Group>(ARG_GROUP)
    }

    override fun getLayoutId() = R.layout.fragment_group

    override fun getViewModelClass() = GroupFeedViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivHeader.load(group?.photo200)
        tvHeader.text = group?.name?.toLowerCase()
        appBar.addOnOffsetChangedListener(ButtonWatcher())
        btnSubscribe.setOnClickListener { (viewModel as? GroupFeedViewModel)?.subscribeOrNot() }
    }

    override fun prepareViewModel() {
        val viewModel = viewModel as? GroupFeedViewModel ?: return
        group?.let {
            viewModel.init(it)
        }
        viewModel.getAvatar().observe(this, Observer { updateAvatar(it) })
        viewModel.loadAvatar()
        viewModel.getSubscription().observe(this, Observer { invalidateButton(it) })
    }

    private fun updateAvatar(data: Wrapper<Photo>) {
        if (data.data != null) {
            ivHeader.load(data.data.getOptimalPhoto().url)
        }
    }

    private fun invalidateButton(data: Wrapper<Boolean>) {
        if (data.data != null) {
            btnSubscribe.setText(if (data.data) R.string.unsubscribe else R.string.subscribe)
        } else {
            showToast(context, data.error ?: "")
        }
    }

    companion object {

        const val ARG_GROUP = "group"

        fun newInstance(args: Bundle): GroupFeedFragment {
            val fragment = GroupFeedFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private inner class ButtonWatcher : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            btnSubscribe.setVisible(state != State.COLLAPSED)
        }
    }
}