package global.msnthrp.feeed.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseFragment
import global.msnthrp.feeed.prefs.LivePrefs
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var livePrefs: LivePrefs

    override fun getLayoutId() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        initStates()
        initListeners()
    }

    private fun initStates() {
        swHideAds.isChecked = livePrefs.hideAds.getValue()
        stColumns.value = livePrefs.gridColumns.getValue()
    }

    private fun initListeners() {
        swHideAds.setOnCheckedChangeListener { _, isChecked -> livePrefs.hideAds.set(isChecked) }
        stColumns.onValueChangedListener = { livePrefs.gridColumns.set(it) }
    }

    companion object {

        fun newInstance() = SettingsFragment()
    }

}