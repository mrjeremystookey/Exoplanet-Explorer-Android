package r.stookey.exoplanetexplorer.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import r.stookey.exoplanetexplorer.ui.compose.theme.ReferenceWebView


private const val ARG_REFERENCE = "param1"
private const val ARG_TITLE = "param2"

class ReferenceFragment : Fragment() {

    private var reference: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            reference = it.getString(ARG_REFERENCE)
            title = it.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(){
                    Text("This is a test")
                    ReferenceWebView(reference = reference!!)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param reference Parameter 1.
         * @param title Parameter 2.
         * @return A new instance of fragment ReferenceFragment.
         */
        @JvmStatic
        fun newInstance(reference: String, title: String) =
            ReferenceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_REFERENCE, reference)
                    putString(ARG_TITLE, title)
                }
            }
    }
}