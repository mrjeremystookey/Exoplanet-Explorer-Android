package r.stookey.exoplanetexplorer.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import r.stookey.exoplanetexplorer.ui.compose.theme.ReferenceWebView
import timber.log.Timber



class WebViewReferenceFragment : Fragment() {

    private val args: WebViewReferenceFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Column {
                    ReferenceWebView(reference = args.referenceUrl)
                }
            }
        }
    }

}
