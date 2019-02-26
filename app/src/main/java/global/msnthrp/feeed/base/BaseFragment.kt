package global.msnthrp.feeed.base

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.lang.IllegalStateException
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    protected val safeContext: Context
        get() = context ?: activity?.applicationContext ?: throw IllegalStateException("Context leaked away!")

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(activity, getLayoutId(), null)


}