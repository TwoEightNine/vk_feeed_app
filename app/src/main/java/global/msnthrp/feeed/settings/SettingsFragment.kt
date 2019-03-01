package global.msnthrp.feeed.settings

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import global.msnthrp.feeed.App
import global.msnthrp.feeed.BuildConfig
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseFragment
import global.msnthrp.feeed.login.LoginActivity
import global.msnthrp.feeed.prefs.LivePrefs
import global.msnthrp.feeed.storage.Session
import global.msnthrp.feeed.utils.showConfirm
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var livePrefs: LivePrefs

    override fun getLayoutId() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        initStates()
        initListeners()
        tvVersion.text = getString(
            R.string.version,
            getString(R.string.app_name),
            BuildConfig.VERSION_NAME,
            BuildConfig.BUILD_TIME
        )

        tvAccount.text = session.userName.toLowerCase()
        btnLogOut.setOnClickListener { logOut() }
    }

    private fun initStates() {
        swHideAds.isChecked = livePrefs.hideAds.getValue()
        stColumns.value = livePrefs.gridColumns.getValue()
    }

    private fun initListeners() {
        swHideAds.setOnCheckedChangeListener { _, isChecked -> livePrefs.hideAds.set(isChecked) }
        stColumns.onValueChangedListener = { livePrefs.gridColumns.set(it) }
    }

    private fun logOut() {
        showConfirm(context, getString(R.string.want_to_log_out)) { logOut ->
            if (logOut) {
                session.logOut()
                activity?.finish()
                LoginActivity.launch(context, true)
            }
        }
    }

    companion object {

        fun newInstance() = SettingsFragment()
    }

}