package global.msnthrp.feeed.settings

import android.os.Bundle
import android.view.View
import android.widget.Switch
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseFragment
import global.msnthrp.feeed.storage.Prefs
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var prefs: Prefs

    override fun getLayoutId() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        initStates()
        initListeners()
    }

    private fun initStates() {
        swHideAds.isChecked = prefs.hideAds
        stColumns.value = prefs.columnsCount
    }

    private fun initListeners() {
        swHideAds.setOnCheckedChangeListener { _, isChecked -> prefs.hideAds = isChecked }
        stColumns.onValueChangedListener = { prefs.columnsCount = it }
    }

    companion object {

        fun newInstance() = SettingsFragment()
    }

}